package com.example.googlemapdemoall

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemapdemoall.litemode.LiteModeActivity1
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), OnMapReadyCallback, PopupMenu.OnMenuItemClickListener, GoogleMap.OnPoiClickListener {

    private lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.main_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        main_menu.setOnClickListener {
            menuup()
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

//        config POI
        googleMap.setOnPoiClickListener(this)

//        باز شدن نقشه در لوکیشن خاص
        val latLng = LatLng(35.689927429417054, 51.311302185058594)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))


//        config baraye map
        val option = GoogleMapOptions()
        option.mapType(2)
        googleMap.mapType = option.mapType


        toast("نقشه با موقعیت بارگذاری شد.")
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_maptype -> {
                showMaptype()
                true
            }
            R.id.item_liteMode -> {
                startActivity<LiteModeActivity1>()
                true
            }
        else -> false
    }


}

private fun showMaptype() {
    val values =
            arrayOf("Normal", "Hybrid", "Satelite", "Terrain", "None")
    val builder =
            AlertDialog.Builder(this)
    builder.setTitle("Select Map type:")
    builder.setSingleChoiceItems(
            values, -1
    ) { dialog, item ->
        when (item) {
            0 -> {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
                dialog.dismiss()
            }
            1 -> {
                googleMap.setMapType(4)
                dialog.dismiss()
            }
            2 -> {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
                dialog.dismiss()
            }
            3 -> {
                googleMap.setMapType(3)
                dialog.dismiss()
            }
            4 -> {
                googleMap.setMapType(0)
                dialog.dismiss()
            }
        }
    }

    builder.show()

}

fun menuup() {
    val popup = PopupMenu(this, main_menu)
    popup.menuInflater.inflate(R.menu.item_menu, popup.menu)
    popup.setOnMenuItemClickListener(this)
    popup.show()
}


override fun onPoiClick(p0: PointOfInterest) {
    Log.e("hassan", "onPoiClick: " + p0.latLng)
    Log.e("hassan", "onPoiClick: " + p0.name)
    Log.e("hassan", "onPoiClick: " + p0.placeId)
}

}