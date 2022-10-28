package org.wit.mygolftracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.mygolftracker.R
//import org.wit.mygolftracker.activities.databinding.ActivityMapsBinding
import org.wit.mygolftracker.databinding.ActivityMapsBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfCourseModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var app: MainApp
    val golfCourseLocations = mutableListOf<LatLng>()
    var golfCourses = listOf<GolfCourseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        mMap.clear()

        golfCourses = app.golfRounds.findAllCourses()
        //val golfCourseLocations = mutableListOf<LatLng>()
        golfCourses.forEach { it ->
            val newLatLng = LatLng(it.lat, it.lng)
            golfCourseLocations.add(newLatLng)
        }

        // Add a marker for each golf course user has played
        if (golfCourses.size > 0) {
            golfCourses.forEach { it ->
                if (it.roundsPlayed > 0) {
                val loc = LatLng(it.lat, it.lng)
                    //val icon = R.drawable.${it.icon}
                mMap.addMarker(
                    MarkerOptions().position(loc).title("${it.title}")
                        .snippet("Rounds played: ${it.roundsPlayed}")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.golf))
                        .flat(true)

                )
                }
            }

            val cameraMove = LatLng(golfCourses[0].lat, golfCourses[0].lng)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(golfCourseLocations[0], 10f))
        }
    }
}