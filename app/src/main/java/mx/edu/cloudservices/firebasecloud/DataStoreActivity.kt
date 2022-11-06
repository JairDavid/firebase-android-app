package mx.edu.cloudservices.firebasecloud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.cloudservices.auth.LoginFirebaseActivity
import mx.edu.cloudservices.databinding.ActivityDataStoreBinding
import mx.edu.cloudservices.firebasecloud.firecrud.DataStoreListActivity
import mx.edu.cloudservices.firebasecloud.firecrud.DataStoreRegisterActivity
import mx.edu.cloudservices.mapas.MapsActivity

class DataStoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fireSave.setOnClickListener {
            startActivity(Intent(this, DataStoreRegisterActivity::class.java))
        }

        binding.fireQuery.setOnClickListener {
            startActivity(Intent(this, DataStoreListActivity::class.java))
        }

        binding.session.setOnClickListener {
            startActivity(Intent(this, LoginFirebaseActivity::class.java))
        }

        binding.maps.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }


    }
}