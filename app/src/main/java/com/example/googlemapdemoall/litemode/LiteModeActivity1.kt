package com.example.googlemapdemoall.litemode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_lite_mode.*
import kotlinx.android.synthetic.main.item_litemode_recyclerview.*
import kotlinx.android.synthetic.main.item_litemode_recyclerview.view.*

class LiteModeActivity1 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.googlemapdemoall.R.layout.activity_lite_mode)


        recyclerView = recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setAdapter(MapAdapter(LiteModeActivity1.Companion.LIST_LOCATIONS))
        recyclerView.setRecyclerListener(mRecyclerViewListener)
    }

    //listerner
    private val mRecyclerViewListener =
        RecyclerView.RecyclerListener { holder ->
            val mapHolder: LiteModeActivity1.MapAdapter.ViewHolder =
                holder as LiteModeActivity1.MapAdapter.ViewHolder
            if (mapHolder != null && mapHolder.gMap != null) {
                mapHolder.gMap!!.clear()
                mapHolder.gMap!!.setMapType(0)
            }
        }

    //adapter
    private inner class MapAdapter(Locations: Array<LiteModeActivity1.NameLocation>) :
        RecyclerView.Adapter<LiteModeActivity1.MapAdapter.ViewHolder>() {
        private val nameLocations: Array<LiteModeActivity1.NameLocation> = Locations
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): LiteModeActivity1.MapAdapter.ViewHolder {
            var view: View? = null
            if (view == null) {
                view = LayoutInflater.from(baseContext)
                    .inflate(com.example.googlemapdemoall.R.layout.item_litemode_recyclerview, parent, false)
            }
            return ViewHolder(view!!);
        }

        override fun onBindViewHolder(
            holder: LiteModeActivity1.MapAdapter.ViewHolder,
            position: Int
        ) {
            if (holder == null) {
                return
            }
            holder.bindView(position)
        }

        override fun getItemCount(): Int {
            return nameLocations.size
        }

        internal inner class ViewHolder(var layout: View) :
            RecyclerView.ViewHolder(layout), OnMapReadyCallback {

            private  var mapView: MapView
            val title: TextView
            var gMap: GoogleMap? = null

            init {
                mapView = itemView.mapview
                title = itemView.txt_title
                mapView.onCreate(null)
                mapView.getMapAsync(this)
            }

            override fun onMapReady(googleMap: GoogleMap) {
                MapsInitializer.initialize(applicationContext)
                gMap = googleMap
                setMapLocation()
            }

            private fun setMapLocation() {
                if (gMap == null) return
                val data: LiteModeActivity1.NameLocation =
                    mapView.tag as LiteModeActivity1.NameLocation
                        ?: return
                gMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f))
                gMap!!.addMarker(MarkerOptions().position(data.location))
                gMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

            fun bindView(pos: Int) {
                val item: LiteModeActivity1.NameLocation =
                    nameLocations[pos]
                layout.tag = this
                mapView.tag = item
                setMapLocation()
                title.text = item.name
            }
        }
    }

    ///date model
    private class NameLocation(val name: String, val location: LatLng)

    companion object {
        // list of datamodel
        private val LIST_LOCATIONS: Array<LiteModeActivity1.NameLocation> =
            arrayOf<LiteModeActivity1.NameLocation>(
                LiteModeActivity1.NameLocation(
                    "Cape Town",
                    LatLng(-33.920455, 18.466941)
                ),
                LiteModeActivity1.NameLocation(
                    "Beijing",
                    LatLng(39.937795, 116.387224)
                ),
                LiteModeActivity1.NameLocation(
                    "Bern",
                    LatLng(46.948020, 7.448206)
                ),
                LiteModeActivity1.NameLocation(
                    "Breda",
                    LatLng(51.589256, 4.774396)
                ),
                LiteModeActivity1.NameLocation(
                    "Brussels",
                    LatLng(50.854509, 4.376678)
                ),
                LiteModeActivity1.NameLocation(
                    "Copenhagen",
                    LatLng(55.679423, 12.577114)
                ),
                LiteModeActivity1.NameLocation(
                    "Hannover",
                    LatLng(52.372026, 9.735672)
                ),
                LiteModeActivity1.NameLocation(
                    "Helsinki",
                    LatLng(60.169653, 24.939480)
                ),
                LiteModeActivity1.NameLocation(
                    "Hong Kong",
                    LatLng(22.325862, 114.165532)
                ),
                LiteModeActivity1.NameLocation(
                    "Istanbul",
                    LatLng(41.034435, 28.977556)
                ),
                LiteModeActivity1.NameLocation(
                    "Johannesburg",
                    LatLng(-26.202886, 28.039753)
                ),
                LiteModeActivity1.NameLocation(
                    "Lisbon",
                    LatLng(38.707163, -9.135517)
                ),
                LiteModeActivity1.NameLocation(
                    "London",
                    LatLng(51.500208, -0.126729)
                ),
                LiteModeActivity1.NameLocation(
                    "Madrid",
                    LatLng(40.420006, -3.709924)
                ),
                LiteModeActivity1.NameLocation(
                    "Mexico City",
                    LatLng(19.427050, -99.127571)
                ),
                LiteModeActivity1.NameLocation(
                    "Moscow",
                    LatLng(55.750449, 37.621136)
                ),
                LiteModeActivity1.NameLocation(
                    "New York",
                    LatLng(40.750580, -73.993584)
                ),
                LiteModeActivity1.NameLocation(
                    "Oslo",
                    LatLng(59.910761, 10.749092)
                ),
                LiteModeActivity1.NameLocation(
                    "Paris",
                    LatLng(48.859972, 2.340260)
                ),
                LiteModeActivity1.NameLocation(
                    "Prague",
                    LatLng(50.087811, 14.420460)
                ),
                LiteModeActivity1.NameLocation(
                    "Rio de Janeiro",
                    LatLng(-22.90187, -43.232437)
                ),
                LiteModeActivity1.NameLocation(
                    "Rome",
                    LatLng(41.889998, 12.500162)
                ),
                LiteModeActivity1.NameLocation(
                    "Sao Paolo",
                    LatLng(-22.863878, -43.244097)
                ),
                LiteModeActivity1.NameLocation(
                    "Seoul",
                    LatLng(37.560908, 126.987705)
                ),
                LiteModeActivity1.NameLocation(
                    "Stockholm",
                    LatLng(59.330650, 18.067360)
                ),
                LiteModeActivity1.NameLocation(
                    "Sydney",
                    LatLng(-33.873651, 151.2068896)
                ),
                LiteModeActivity1.NameLocation(
                    "Taipei",
                    LatLng(25.022112, 121.478019)
                ),
                LiteModeActivity1.NameLocation(
                    "Tokyo",
                    LatLng(35.670267, 139.769955)
                ),
                LiteModeActivity1.NameLocation(
                    "Tulsa Oklahoma",
                    LatLng(36.149777, -95.993398)
                ),
                LiteModeActivity1.NameLocation(
                    "Vaduz",
                    LatLng(47.141076, 9.521482)
                ),
                LiteModeActivity1.NameLocation(
                    "Vienna",
                    LatLng(48.209206, 16.372778)
                ),
                LiteModeActivity1.NameLocation(
                    "Warsaw",
                    LatLng(52.235474, 21.004057)
                ),
                LiteModeActivity1.NameLocation(
                    "Wellington",
                    LatLng(-41.286480, 174.776217)
                ),
                LiteModeActivity1.NameLocation(
                    "Winnipeg",
                    LatLng(49.875832, -97.150726)
                )
            )
    }
}