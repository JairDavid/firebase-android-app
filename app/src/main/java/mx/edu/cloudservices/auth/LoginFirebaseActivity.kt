package mx.edu.cloudservices.auth

import android.app.Activity
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
import mx.edu.cloudservices.databinding.ActivityLoginFirebaseBinding

class LoginFirebaseActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginFirebaseBinding
    lateinit var google:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("140770636434-ivl5tne4g4ogkn8su276p54e9ka7duvs.apps.googleusercontent.com")
            .requestEmail()
            .build()

        google = GoogleSignIn.getClient(this, options)


        binding.loginBtn.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.userIn.text.toString(), binding.passwordIn.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "[Error]: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.registerBtn.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.userIn.text.toString(), binding.passwordIn.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "[Error]: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.verifyBtn.setOnClickListener {
            var user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                Toast.makeText(this, "${user.email}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Sin sesión", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                Toast.makeText(this, "Error al cerrar sesión ${user.email}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Sin sesión", Toast.LENGTH_SHORT).show()
            }
        }

        binding.googleLoginBtn.setOnClickListener {
            google.signOut()
            google.silentSignIn()

            val googleIntent = google.signInIntent
            getResult.launch(googleIntent)
        }



    }
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        System.out.println(it.resultCode)
        if(it.resultCode == Activity.RESULT_OK){
            val tarea = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            var cuenta = tarea.getResult(ApiException::class.java)
            var credentials = GoogleAuthProvider.getCredential(cuenta.idToken, null)

            FirebaseAuth.getInstance().signInWithCredential(credentials)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "${cuenta.displayName}", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "[Error]: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}