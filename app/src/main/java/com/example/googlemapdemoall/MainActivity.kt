package com.example.googlemapdemoall

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.googlemapdemoall.litemode.LiteModeActivity
import com.example.googlemapdemoall.util.LatLngInterpolator
import com.example.googlemapdemoall.util.MarkerAnimation
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.IOException

class MainActivity : AppCompatActivity(), OnMapReadyCallback, PopupMenu.OnMenuItemClickListener,
    OnPoiClickListener,
    OnInfoWindowClickListener {

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

    //    region config Google Map
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

//        config POI
        googleMap.setOnPoiClickListener(this)

//        باز شدن نقشه در لوکیشن خاص
        val latLng = LatLng(35.689927429417054, 51.311302185058594)
//        googleMap.addMarker(MarkerOptions().title("فرودگاه مهرآباد").position(latLng))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))

//        config baraye map
        val option = GoogleMapOptions()
        option.mapType(1)

        // Contoroler
        googleMap.mapType = option.mapType

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false

//        googleMap.uiSettings.isScrollGesturesEnabled = false
//        googleMap.uiSettings.isTiltGesturesEnabled = false
//        googleMap.uiSettings.isRotateGesturesEnabled = false


        handlePermission()

//        Click in icon location

        googleMap.setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener {
            Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show()
            false
        })

        googleMap.setOnMyLocationClickListener(OnMyLocationClickListener {
            Toast.makeText(this, "i found your location :)", Toast.LENGTH_SHORT)
                .show()
        })


        // region استفاده از شکل ها
        setPolyLine()
        setPolygon()
        setCircle()
        //endregion

//        setGroundOverLay()

//        getCamera()

        googleMap.setOnMapLongClickListener {
            getCamera()
             goLocByLatLng(it);
            //  goLocByName();
            // DialogTrack(latLng);
//            DistanceTo(latLng)
        }


        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                 LatLng (35.684993353356006,
                51.41000412404537
            ), 20f
        ));
        var prev = LatLng(35.684993353356006, 51.41000412404537)
        googleMap.addMarker(
            MarkerOptions()
                .position(prev)
        )

        setDirectaion(prev, prev)


        googleMap.setOnCameraMoveListener {
            val lat = googleMap.cameraPosition.target
            val lng = googleMap.cameraPosition.target.longitude
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat, 20f));
            Log.e("hassan", "$lat , $lng")
//            googleMap.clear();
//            markerAnimation(lat);

            setDirectaion(prev, lat);
            prev =  lat

        }


    }
    //endregion


    //    region تغییر لوکیشن با انیمیشن
    private fun markerAnimation(latLng: LatLng) {
        val firstLocation = LatLng(35.684993353356006, 51.41000412404537)
        val toLocation = LatLng(latLng.latitude, latLng.longitude)
        val marker: Marker = googleMap.addMarker(MarkerOptions().position(firstLocation))
        val markerAnimation = MarkerAnimation()
        markerAnimation.animateMarkerToGB(marker, toLocation, LatLngInterpolator.Spherical())
    }
//    endregion


    //region handlePermission
    private fun handlePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("hassan", "true")
            googleMap.setMyLocationEnabled(true)
        } else {
            Log.e("hassan", "false")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constant.LOCATION_PERMISSION
            )
            toast("for use location map you need this permission!")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == Constant.LOCATION_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handlePermission()
            } else {
                finish()
            }
        }
    }

    //endregion


    //region addmarkert
    private fun addmarket(name: String, postion: LatLng) {
        val market = MarkerOptions()
//        market.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        market.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_bus))
        market.position(postion).title(name).snippet("این لوکیشن خاصی است")
        googleMap.clear()
        googleMap.setOnInfoWindowClickListener(this)
//        googleMap.addMarker(market)
    }

    override fun onInfoWindowClick(marker: Marker) {
        toast(marker.title)
    }

    //endregion


    //region handel menu
    fun menuup() {
        val popup = PopupMenu(this, main_menu)
        popup.menuInflater.inflate(R.menu.item_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    // handel items munu
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_maptype -> {
                showMaptype()
                true
            }
            R.id.item_liteMode -> {
                startActivity<LiteModeActivity>()
                true
            }
            else -> false
        }


    }

    // for handel item maptype in mune
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

//    endregion

    //    region ترسیم شکل
    private fun setPolyLine() {
        googleMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    35.686136831341635,
                    51.410141587257385
                )
            )
        )
        googleMap.addMarker(MarkerOptions().position(LatLng(33.48376889821861, 48.35339426994324)))

        //    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.686136831341635,51.410141587257385),16f));
        val polyline: Polyline = googleMap.addPolyline(
            PolylineOptions()
                .add(
                    LatLng(35.686136831341635, 51.410141587257385),
                    LatLng(33.48376889821861, 48.35339426994324)
                )
                .width(5f)
                .clickable(true)
                .color(Color.RED)
        )


        //     polyline.remove();
    }

    private fun setPolygon() {
        val polygon: Polygon = googleMap.addPolygon(
            PolygonOptions()
                .add(
                    LatLng(35.684993353356006, 51.41000412404537),
                    LatLng(35.6820178918213, 51.40936810523271),
                    LatLng(35.68120851222863, 51.417416073381894),
                    LatLng(35.684242826388406, 51.418069861829274)
                )
                .strokeColor(Color.RED)
                .clickable(true)
                .fillColor(Color.BLUE)
        )

        ///  polygon.remove();
    }

    private fun setCircle() {
        val circle: Circle = googleMap.addCircle(
            CircleOptions()
                .center(LatLng(35.68929649250615, 51.409634314477444))
                .clickable(true)
                .radius(100.0)
                .strokeColor(Color.BLACK)
                .fillColor(Color.TRANSPARENT)
        )
        val circle1: Circle = googleMap.addCircle(
            CircleOptions()
                .center(LatLng(35.688273966543555, 51.41964733600617))
                .clickable(true)
                .radius(100.0)
                .strokeColor(Color.BLACK)
                .fillColor(Color.TRANSPARENT)
        )
    }
//endregion


    private fun getCamera() {
        val simple =
            CameraUpdateFactory.newLatLngZoom(LatLng(35.684993353356006, 51.41000412404537), 20f)
        // gMap.moveCamera(simple);
        googleMap.animateCamera(simple)
    }

    //  region گذاشتن عکس رو نقشه
    private fun setGroundOverLay() {
        val newarkLatLng = LatLng(40.714086, -74.228697)
        val newarkBounds = LatLngBounds(
            LatLng(40.712216, -74.22655),  // South west corner
            LatLng(40.773941, -74.12544)
        ) ///  North east corner
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(40.714086, -74.228697), 10f))
        val goOption = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
            .clickable(true)
            .positionFromBounds(newarkBounds)
        googleMap.addGroundOverlay(goOption)
    }
//endregion

    //    region کشیدن خط با جابجایی خط
    private fun setDirectaion(preLatLng: LatLng, latLng: LatLng) {
        val polyline: Polyline = googleMap.addPolyline(
            PolylineOptions()
                .add(preLatLng, latLng)
                .width(10f)
                .color(Color.BLACK)
        )
    }
//    endregion

    private fun goLocByLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this)
        try {
            val list =
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val address = list[0]
            val lat = address.latitude
            val lng = address.longitude
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 10f))
            googleMap.addMarker(
                MarkerOptions()
                    .title(address.featureName).position(LatLng(lat, lng))

            )
            toast(address.featureName.toString())
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            Log.e("hassan",e.toString())
        }
    }


    override fun onPoiClick(p0: PointOfInterest) {
        Log.e("hassan", "onPoiClick: " + p0.latLng)
        Log.e("hassan", "onPoiClick: " + p0.name)
        Log.e("hassan", "onPoiClick: " + p0.placeId)


        googleMap.setOnMapClickListener {
            Log.e("hassan", it.toString())
//            addmarket(p0.name, it)
        }
    }


}