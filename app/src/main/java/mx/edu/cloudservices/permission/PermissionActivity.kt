package mx.edu.cloudservices.permission

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import mx.edu.cloudservices.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {
    lateinit var binding:ActivityPermissionBinding

    var REQUEST = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkPermission.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                solicitarPermisos()
            }else{
                lanzarAplicacion()
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST -> {
                for (i in grantResults.indices){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i])){

                            when(permissions[i]){
                                "android.permission.CAMERA" -> mostrarAlerta("Falto permiso de camara")
                                "android.permission.RECORD_AUDIO" -> mostrarAlerta("Falto permiso de audio")
                                "android.permission.CALL_PHONE" ->mostrarAlerta("Falto permiso de llamada")
                                "android.permission.ACCESS_COARSE_LOCATION" -> mostrarAlerta("Falto permiso de ubicacion")
                                "android.permission.READ_EXTERNAL_STORAGE" -> mostrarAlerta("Falto permiso de almacenamiento")
                            }

                        }else{
                            Toast.makeText(this, "No se puede mostrar el dialogo", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun mostrarAlerta(permiso:String) {
        var dialog = AlertDialog.Builder(this)
        dialog.setMessage(permiso).setCancelable(false).setPositiveButton("OK"){dialog, id->
        }
        var alerta = dialog.create()
        alerta.show()
    }

    private fun lanzarAplicacion() {
        Toast.makeText(this, "TIENE TODOS LOS PERMISOS", Toast.LENGTH_SHORT).show()
    }

    private fun solicitarPermisos() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST)
    }


}