package mx.edu.cloudservices.practiceAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import mx.edu.cloudservices.InitConfigActivity
import mx.edu.cloudservices.databinding.ActivityWelcomeAuthBinding

class WelcomeAuthActivity : AppCompatActivity() {
    lateinit var binding:ActivityWelcomeAuthBinding
    lateinit var google: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.currentSessionEmail.text = InitConfigActivity.shared.getSessionEmail()


        binding.logoutAuth.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                Toast.makeText(this, "Error al cerrar sesión ${user.email}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginAuthActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                InitConfigActivity.shared.deleteSession()
                startActivity(Intent(this, LoginAuthActivity::class.java))
                finish()
            }
        }

    }
}