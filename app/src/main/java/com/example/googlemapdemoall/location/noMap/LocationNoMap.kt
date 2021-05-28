package com.example.googlemapdemoall.location.noMap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemapdemoall.R
import com.example.googlemapdemoall.location.noMap.LocationNoMap

class LocationNoMap : AppCompatActivity() {
    var btnShowLocation: Button? = null
    var txtGetLocation: TextView? = null

    //
    ///
    var gpsTracker: GPSTracker? = null
    @SuppressLint("SetTextI18n")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_no_map)
        initView()
        btnShowLocation!!.setOnClickListener {
            gpsTracker = GPSTracker(this@LocationNoMap)
            Log.e("hassan55",gpsTracker.toString())
            Log.e("hassan55", gpsTracker!!.CanGetLocation().toString())


            gpsTracker!!.ShowSettingAlert()


            if (gpsTracker!!.CanGetLocation()) {
                val lat = gpsTracker!!.getLatitude()
                val lng = gpsTracker!!.getLongitude()
                txtGetLocation!!.text = """
                Your Location Found !!!
                Your Latitude:$lat
                Your Longitude:$lng
                """.trimIndent()
                Log.e(
                    TAG,
                    "GPSTracker: $lat,$lng"
                )
            } else {

                Log.e("hassan","kkkkkkk")
                gpsTracker!!.ShowSettingAlert()
            }
        }
    }

    fun initView() {
        btnShowLocation = findViewById(R.id.button)
        txtGetLocation = findViewById(R.id.textview)
    }

    companion object {
        private const val TAG = "TAGS"
    }
}