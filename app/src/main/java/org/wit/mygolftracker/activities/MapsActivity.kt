package org.wit.mygolftracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

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
import timber.log.Timber.i

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.clear()

        golfCourses = app.golfRounds.findAllCourses()
        var avgLat = mutableListOf<Double>()
        var avgLng = mutableListOf<Double>()

        // Add a marker for each golf course user has played
        if (golfCourses.size > 0) {
            golfCourses.forEach { it ->
                if (it.roundsPlayed > 0) {
                    val loc = LatLng(it.lat, it.lng)
                    avgLat.add(it.lat)
                    avgLng.add(it.lng)
                    mMap.addMarker(
                    MarkerOptions().position(loc).title("${it.title}")
                        .snippet("Rounds played: ${it.roundsPlayed}")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.golf))
                        .flat(true)
                    )
                }
            }

            val cameraMove = LatLng(golfCourses[0].lat, golfCourses[0].lng)
            val avgLatLng = LatLng(avgLat.average(), avgLng.average())
            i("Average: " + avgLatLng)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(avgLatLng, 7.5f))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_imagegallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}