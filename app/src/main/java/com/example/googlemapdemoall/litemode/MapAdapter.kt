package com.example.googlemapdemoall.litemode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemapdemoall.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.item_litemode_recyclerview.view.*
import org.jetbrains.anko.toast


class MapAdapter(Locations: Array<NameLocation>,val context: Context) :
    RecyclerView.Adapter<MapAdapter.ViewHolder>() {

    private val nameLocations: Array<NameLocation> = Locations

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MapAdapter.ViewHolder {
        var view: View? = null
        if (view == null) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_litemode_recyclerview, parent, false)
        }
        return ViewHolder(view!!);
    }

    override fun onBindViewHolder(
        holder: MapAdapter.ViewHolder,
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

     inner class ViewHolder(var layout: View) :
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
            MapsInitializer.initialize(context)
            gMap = googleMap
            setMapLocation()
        }

        fun setMapLocation() {
            if (gMap == null) return
            val data: NameLocation =
                mapView.tag as NameLocation
                    ?: return
            gMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f))
            gMap!!.addMarker(MarkerOptions().position(data.location))
            gMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        }

        fun bindView(pos: Int) {
            val item: NameLocation = nameLocations[pos]
            layout.tag = this
            mapView.tag = item
            setMapLocation()
            title.text = item.name
        }
    }
}
