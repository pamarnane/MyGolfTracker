package ie.marnane.mygolftracker.ui.map

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerManager.findAllCourses
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import timber.log.Timber

class MapsFragment : Fragment() {
    private lateinit var mMap : GoogleMap
    private lateinit var golfRounds: List<GolfRoundModel>
    private lateinit var mapsViewModel: MapsViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private var _firebaseUser : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                mapsViewModel.liveFirebaseUser.value = firebaseUser
                _firebaseUser = firebaseUser.uid
                mapsViewModel.getAllCourses(_firebaseUser)
            }
        })

        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)

       mapsViewModel.observableRoundsList.observe(viewLifecycleOwner, Observer { result ->
          if (result != null) {
              Timber.i("MapsFragmentPatrick:" + result.toString())
             // updateMap(result)
          }
      })
        return  inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        /*{
                googleMap -> mMap = googleMap
            mapReady = true
            //updateMap(golfRounds)
            var test = mapsViewModel.getAllCourses()
        }*/
    }

    private val callback = OnMapReadyCallback { mMap ->

        var rounds: LiveData<List<GolfRoundModel>>? = null
        if (_firebaseUser != "") {
             rounds = mapsViewModel.getAllCourses(_firebaseUser)
            Timber.i(rounds.toString())
        }

        var golfCourses = findAllCourses()
        var avgLat = mutableListOf<Double>()
        var avgLng = mutableListOf<Double>()
        var dict = mutableMapOf<String, Int>()

        if (rounds != null) {
            rounds.value?.forEach {
                if (!dict.containsKey(it.course)) {
                    dict[it.course] = 1
                }else{
                    var i = dict[it.course]
                    if (i != null) {
                        dict[it.course] = i + 1
                    }
                }

            }
        }

        golfCourses.forEach {
            var latlng: LatLng = LatLng(it.lat, it.lng)
            avgLat.add(it.lat)
            avgLng.add(it.lng)

            if (dict.containsKey(it.title)) {
                mMap.addMarker(MarkerOptions()
                    .position(latlng)
                    //.snippet("Rounds played: ${it.roundsPlayed}")
                    .snippet("Rounds played: ${dict[it.title]}")
                    .flat(true)
                    .title(it.title)
                )
            }
        }
            val averageLatLng = LatLng(avgLat.average(), avgLng.average())
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(averageLatLng, 7.5f))
        }
}