package mx.edu.cloudservices

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.cloudservices.practiceAuth.SharedStorage

class InitConfigActivity : Application() {
    companion object{
        lateinit var shared:SharedStorage
    }
    override fun onCreate() {
        super.onCreate()
        shared = SharedStorage(applicationContext)
    }
}