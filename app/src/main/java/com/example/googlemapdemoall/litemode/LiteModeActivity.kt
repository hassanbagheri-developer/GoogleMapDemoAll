//package com.example.googlemapdemoall.litemode
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.googlemapdemoall.R
//import com.google.android.gms.maps.model.LatLng
//import kotlinx.android.synthetic.main.activity_lite_mode.*
//
//class LiteModeActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lite_mode)
//
//        val LIST_LOCATIONS = listOf<NameLocation>(
//            NameLocation("Cape Town", LatLng(-33.920455, 18.466941)),
//            NameLocation("Beijing", LatLng(39.937795, 116.387224)),
//            NameLocation("Bern", LatLng(46.948020, 7.448206)),
//            NameLocation("Breda", LatLng(51.589256, 4.774396)),
//            NameLocation("Brussels", LatLng(50.854509, 4.376678)),
//            NameLocation("Copenhagen", LatLng(55.679423, 12.577114)),
//            NameLocation("Hannover", LatLng(52.372026, 9.735672)),
//            NameLocation("Helsinki", LatLng(60.169653, 24.939480)),
//            NameLocation("Hong Kong", LatLng(22.325862, 114.165532)),
//            NameLocation("Istanbul", LatLng(41.034435, 28.977556)),
//            NameLocation("Johannesburg", LatLng(-26.202886, 28.039753)),
//            NameLocation("Lisbon", LatLng(38.707163, -9.135517)),
//            NameLocation("London", LatLng(51.500208, -0.126729)),
//            NameLocation("Madrid", LatLng(40.420006, -3.709924)),
//            NameLocation("Mexico City", LatLng(19.427050, -99.127571)),
//            NameLocation("Moscow", LatLng(55.750449, 37.621136)),
//            NameLocation("New York", LatLng(40.750580, -73.993584)),
//            NameLocation("Oslo", LatLng(59.910761, 10.749092)),
//            NameLocation("Paris", LatLng(48.859972, 2.340260)),
//            NameLocation("Prague", LatLng(50.087811, 14.420460)),
//            NameLocation("Rio de Janeiro", LatLng(-22.90187, -43.232437)),
//            NameLocation("Rome", LatLng(41.889998, 12.500162)),
//            NameLocation("Sao Paolo", LatLng(-22.863878, -43.244097)),
//            NameLocation("Seoul", LatLng(37.560908, 126.987705)),
//            NameLocation("Stockholm", LatLng(59.330650, 18.067360)),
//            NameLocation("Sydney", LatLng(-33.873651, 151.2068896)),
//            NameLocation("Taipei", LatLng(25.022112, 121.478019)),
//            NameLocation("Tokyo", LatLng(35.670267, 139.769955)),
//            NameLocation("Tulsa Oklahoma", LatLng(36.149777, -95.993398)),
//            NameLocation("Vaduz", LatLng(47.141076, 9.521482)),
//            NameLocation("Vienna", LatLng(48.209206, 16.372778)),
//            NameLocation("Warsaw", LatLng(52.235474, 21.004057)),
//            NameLocation("Wellington", LatLng(-41.286480, 174.776217)),
//            NameLocation("Winnipeg", LatLng(49.875832, -97.150726))
//        )
//
//
//
//        recyclerview.setHasFixedSize(true)
//        recyclerview.setLayoutManager(LinearLayoutManager(this))
//        val adapter = RecylerAdapterNameLocation(LIST_LOCATIONS,this)
//        recyclerview.setAdapter(adapter)
//
//
//
//    }
//}