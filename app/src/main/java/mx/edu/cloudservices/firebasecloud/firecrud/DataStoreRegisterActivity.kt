package mx.edu.cloudservices.firebasecloud.firecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.cloudservices.databinding.ActivityDataStoreRegisterBinding
import mx.edu.cloudservices.firebasecloud.DataStoreActivity

class DataStoreRegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityDataStoreRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fireSaveBtn.setOnClickListener {
            var sexo = ""

            if(binding.femaleSex.isChecked){
                sexo = "Femenino"
            }else if (binding.maleSex.isChecked){
                sexo = "Masculino"
            }

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .add(hashMapOf(

                    "nombre" to binding.name.text.toString(),
                    "paterno" to binding.firstname.text.toString(),
                    "materno" to binding.lastname.text.toString(),
                    "edad" to binding.age.text.toString().toInt(),
                    "genero" to sexo

                )).addOnSuccessListener {
                    Toast.makeText(this, "Registrado correctamente!", Toast.LENGTH_SHORT).show()
                    binding.name.text.clear()
                    binding.firstname.text.clear()
                    binding.lastname.text.clear()
                    binding.age.text.clear()
                    binding.groupGender.clearCheck()

                }.addOnFailureListener {
                    Toast.makeText(this, "[Error]: Falló el registro", Toast.LENGTH_SHORT).show()
                }
        }

        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this, DataStoreActivity::class.java))
            finish()
        }
    }
}