package com.example.googlemapdemoall.location.Map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.googlemapdemoall.Constant
import com.example.googlemapdemoall.R
import com.example.googlemapdemoall.location.Map.LocationWithMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationWithMap : AppCompatActivity() {
    private var gMap: GoogleMap? = null
    var supportMapFragment: SupportMapFragment? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var latitude = 0.0
    var longtiude = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_with_map)
        initReqMap()
        handlePermission()
    }

    private fun handlePermission() {
        if (ActivityCompat.checkSelfPermission(
                this@LocationWithMap,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            currentLocation
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constant.LOCATION_PERMISSION
            )
            Toast.makeText(
                this,
                "for use location map you need this permission!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constant.LOCATION_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLocation
            } else {
                finish()
            }
        }
    }

    fun initReqMap() {
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    val currentLocation: Unit
        get() {
            @SuppressLint("MissingPermission") val task =
                fusedLocationProviderClient!!.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longtiude = location.longitude
                    supportMapFragment!!.getMapAsync { googleMap ->
                        gMap = googleMap
                        gMap!!.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    latitude,
                                    longtiude
                                ), 10f
                            )
                        )
                        gMap!!.uiSettings.isZoomControlsEnabled = true
                        gMap!!.uiSettings.isCompassEnabled = true
                        gMap!!.addMarker(
                            MarkerOptions().position(LatLng(latitude, longtiude))
                                .title("Your Location")
                        )
                        Log.e(
                            TAG,
                            "FusedLocationProviderClient: $latitude,$longtiude"
                        )
                    }
                }
            }
        }

    companion object {
        private const val TAG = "TAGS"
    }
}