package mx.edu.cloudservices.firebasecloud.firecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.cloudservices.databinding.ActivityDataStoreDetailBinding

class DataStoreDetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityDataStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}