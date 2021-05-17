//package com.example.googlemapdemoall.litemode
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.googlemapdemoall.R
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapsInitializer
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.model.MarkerOptions
//import kotlinx.android.synthetic.main.item_litemode_recyclerview.view.*
//import org.jetbrains.anko.toast
//
//
//class RecylerAdapterNameLocation(private val data: List<NameLocation>, val context: Context) :
//    RecyclerView.Adapter<RecylerAdapterNameLocation.ViewHolder>() {
//
//    private  var mapView:com.google.android.gms.maps.MapView? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        ViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(
//                    R.layout.item_litemode_recyclerview,
//                    parent,
//                    false
//                ),
//            null
//
//
//        )
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setData(data[position])
//    }
//
//
//    override fun getItemCount() = data.size
//
//
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) ,
//        OnMapReadyCallback{
//
//        private val mapView = itemView.mapview
//        private val title = itemView.txt_title
//        private val layout = itemView
//
//
//
//        override fun onMapReady(googleMap: GoogleMap) {
//            context.toast("tttttttttt")
//            MapsInitializer.initialize(context)
//            gMap = googleMap
//            setMapLocation()
//        }
//
//        private fun setMapLocation() {
//            if (gMap == null) return
//            val data = mapView?.getTag() as NameLocation ?: return
//            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(data.loaction, 13f))
//            gMap.addMarker(MarkerOptions().position(data.loaction))
//            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
//        }
//
//
//
//
//
//
//        fun setData(data: NameLocation) {
//
//            mapView.onCreate(null)
//            mapView.getMapAsync(this)
//
//
//            layout.tag = this
//            mapView.tag = data
//            title.text = data.name
//
//
//        }
//
//
//    }
//}
//
