package mx.edu.cloudservices.practiceAuth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import mx.edu.cloudservices.InitConfigActivity
import mx.edu.cloudservices.databinding.ActivityLoginAuthBinding
import mx.edu.cloudservices.databinding.ActivityLoginFirebaseBinding
import mx.edu.cloudservices.databinding.ActivityMapsBinding

class LoginAuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginAuthBinding
    lateinit var google: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("140770636434-ivl5tne4g4ogkn8su276p54e9ka7duvs.apps.googleusercontent.com")
            .requestEmail()
            .build()

        google = GoogleSignIn.getClient(this, options)

        binding.loginAuth.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.emailAuth.text.toString(), binding.passwordAuth.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        //Redireccionar a WelcomeAuth pasandole la sesión
                        var email  = it.getResult().user?.email
                        Toast.makeText(this, "Iniciado sesión correctamente ${email}", Toast.LENGTH_SHORT).show()
                        if (email != null) {
                            InitConfigActivity.shared.saveUser(email)
                        }
                        startActivity(Intent(this, WelcomeAuthActivity::class.java))
                    }else{
                        Toast.makeText(this, "[Error]: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.googleAuth.setOnClickListener {
            google.signOut()
            google.silentSignIn()

            val googleIntent = google.signInIntent
            getResult.launch(googleIntent)
        }

    }
    val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val tarea = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            var cuenta = tarea.getResult(ApiException::class.java)
            var credentials = GoogleAuthProvider.getCredential(cuenta.idToken, null)

            FirebaseAuth.getInstance().signInWithCredential(credentials)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        //Redireccionar a WelcomeAuth pasandole la sesión
                        var email  = it.getResult().user?.email
                        Toast.makeText(this, "Iniciado sesión correctamente ${email}", Toast.LENGTH_SHORT).show()
                        if (email != null) {
                            InitConfigActivity.shared.saveUser(email)
                        }
                        startActivity(Intent(this, WelcomeAuthActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "[Error]: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}