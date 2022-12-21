package ie.marnane.mygolftracker.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.models.GolfCourseModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.models.GolfTrackerManager.findAllCourses

class MapsFragment : Fragment() {
    private lateinit var mMap : GoogleMap
    private lateinit var golfCourses: List<GolfCourseModel>
    private lateinit var mapsViewModel: MapsViewModel
    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //mapsViewModel = ViewModelProvider(this).get(mapsViewModel::class.java)
        return  inflater.inflate(R.layout.fragment_maps, container, false)
       // return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { mMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mapReady = true

        var golfCourses = findAllCourses()
        var avgLat = mutableListOf<Double>()
        var avgLng = mutableListOf<Double>()




        golfCourses.forEach {
            var latlng: LatLng = LatLng(it.lat, it.lng)
            avgLat.add(it.lat)
            avgLng.add(it.lng)
            mMap.addMarker(MarkerOptions()
                .position(latlng)
                .snippet("Rounds played: ${it.roundsPlayed}")
                .flat(true)
                .title(it.title)
                )
        }




            val averageLatLng = LatLng(avgLat.average(), avgLng.average())
        mMap.moveCamera(CameraUpdateFactory.newLatLng(averageLatLng))
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       /* mapsViewModel.observableCoursesList.observe(viewLifecycleOwner, Observer {
                golfCourses -> this.golfCourses = golfCourses
            updateMap()
        })*/
    }

    private fun updateMap() {
        if (mapReady && golfCourses != null) {
            golfCourses.forEach {
                    golfCourses ->
                if (!golfCourses.lng.isNaN() && !golfCourses.lat.isNaN()) {
                    val marker = LatLng(golfCourses.lng, golfCourses.lat)
                    mMap.addMarker(MarkerOptions().position(marker).title(golfCourses.title))
                }
            }
        }
    }
}