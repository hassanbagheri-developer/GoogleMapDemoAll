package com.example.googlemapdemoall.location.noMap

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.googlemapdemoall.location.noMap.GPSTracker
import org.jetbrains.anko.toast

class GPSTracker(private val context: Context) : Service(),
    LocationListener {
    var isGPSEnabled = false
    var isNetWorkEnabled = false
    var canGetLocation = false
    private var location: Location? = null
    private var latitude = 0.0
    private var longitude = 0dddd.0
    private var locationManager: :x:xLocationManager? = null

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetWorkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            Log.e("hassannnnn","$isNetWorkEnabled")



            if (!isGPSEnabled && !isNetWorkEnabled) {
            } else {
                canGetLocation = true

                if (isNetWorkEnabled) {
                    Log.e("hassan","nilo5")

                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BTW_UPDATE,
                        MIN_DISTANCE_CHANGE_FOR_UPDATE.toFloat(), this
                    )
                    if (locationManager != null) {
                        Log.e("hassan","nilo6")

                        location =
                            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            Log.e("hassan","nilo7")


                            Log.e("hassan2",location.toString())
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                            Log.e("hassan","ok1")
                        }
                    }
                }
                if (isGPSEnabled) {
                    Log.e("hassan","nilo8")

                    if (location == null) {
                        Log.e("hassan","nilo9")

                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BTW_UPDATE,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE.toFloat(),
                            this
                        )
                        if (locationManager != null) {
                            Log.e("hassan","nilo10")

                            Log.e("hassan22",location.toString())
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                            toast("okkkkkkkkkkk")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("hassan","nilo1111111111")

            e.printStackTrace()
            Toast.makeText(context, "" + e.message, Toast.LENGTH_SHORT).show()
        }
        return location
    }

    fun StopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    fun CanGetLocation(): Boolean {
        return canGetLocation
    }

    fun getLatitude(): Double {
        if (location != null) {
            Log.e("hassan3",location.toString())
            latitude = location!!.latitude
        }
        return latitude
    }

    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }
        return longitude
    }

    fun ShowSettingAlert() {
        val builder =
            AlertDialog.Builder(context)
        builder.setTitle("GPS is Settings")
        builder.setMessage("GPS is not Enabled.Do YOU want to go to settings menu ??")
        builder.setPositiveButton("Settings") { dialog, which ->
            val intent =
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}


    companion object {
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATE: Long = 10
        private const val MIN_TIME_BTW_UPDATE = 1000 * 60 * 1.toLong()
    }

    init {
        getLocation()
    }
}