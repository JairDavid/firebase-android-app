package mx.edu.cloudservices.mapas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.edu.cloudservices.R
import mx.edu.cloudservices.databinding.ActivityMapsBinding
import java.util.*
import java.util.logging.Logger

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.search.setOnClickListener {
            //abrirNavegacion(LatLng(26.9296573,-105.6886764), LatLng(26.9332924, -105.668552))
            searchDirection(binding.customCoords.text.toString())
            //getDirection(26.9296573,-105.6886764)
        }
        //calcularDistancia()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
    }

    fun cameraListener(){
        mMap.setOnCameraIdleListener {
            val lat = mMap.cameraPosition.target.latitude
            val lon =  mMap.cameraPosition.target.longitude
        }
    }

    fun getDirection(lat:Double, lon:Double){
        var geocoder = Geocoder(this, Locale.getDefault())
        var directions = geocoder.getFromLocation(lat,lon, 1)
        if(directions.size>0){
            var pais = directions[0].countryName
            if(pais != null){
                System.out.println("Pais: "+ pais)
            }
            var estado = directions[0].adminArea
            if(estado != null){
                System.out.println("Estado: "+ estado)
            }
            var municipio = directions[0].locality
            if(municipio != null){
                System.out.println("Municipio: "+ municipio)
            }
            var colonia = directions[0].subLocality
            if(colonia != null){
                System.out.println("Colonia: "+ colonia)
            }
            var calle = directions[0].thoroughfare
            if(calle != null){
                System.out.println("Calle: "+ calle)
            }
            var numero = directions[0].subThoroughfare
            if(numero != null){
                System.out.println("Numero: "+ numero)
            }
            var zip = directions[0].postalCode
            if(zip != null){
                System.out.println("Zip: "+ zip)
            }
            var completeDirection = directions[0].getAddressLine(0)
            if(completeDirection!=null){
                print(completeDirection)
            }
        }


    }

    fun searchDirection(dir:String){
        try{

        }catch (e:Exception){
            Log.d("ERROR", "${e}")
        }
        var position  = LatLng(0.0, 0.0)
        var geocoder = Geocoder(this)
        var directions  = geocoder.getFromLocationName(dir, 1)
        if(!directions.isNullOrEmpty()){
            var direction = directions[0]
            position = LatLng(direction.latitude, direction.longitude)
            mMap.addMarker(MarkerOptions().position(position))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10f))
        }
    }

    fun calcularDistancia(){
        var locacion1 = Location("")
        locacion1.latitude = 16.2366061
        locacion1.longitude = -97.2941301
        var locacion2 = Location("")
        locacion2.latitude = 16.244460
        locacion2.longitude = -97.282723

        var distancia  = locacion1.distanceTo(locacion2)
        System.out.println("distancia: "+distancia)
    }

    fun abrirNavegacion(origen:LatLng, destino:LatLng){

        var intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?saddr=" +
                    "${origen.latitude},${origen.longitude}&daddr=" +
                    "${destino.latitude},${destino.longitude}")
        )
        startActivity(intent)

    }

}





















