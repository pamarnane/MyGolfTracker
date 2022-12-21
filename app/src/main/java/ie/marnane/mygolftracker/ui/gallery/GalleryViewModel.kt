package ie.marnane.mygolftracker.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import timber.log.Timber

class GalleryViewModel : ViewModel() {

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    private val golfRounds =
        MutableLiveData<List<GolfRoundModel>>()

    var observableGolfRounds: LiveData<List<GolfRoundModel>>
        get() = golfRounds
        set(value) {golfRounds.value = value.value}

    init { load() }

    fun load() {
        try {
            FirebaseDBManager.findAllwImages(liveFirebaseUser.value?.uid!!, golfRounds)
            Timber.i("Report Load Success : ${golfRounds.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun onQueryTextChange (query: String?) {
        //val golfRounds = FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, golfRounds)
        Transformations.map(observableGolfRounds) { it ->
            it.filter {
                it.course.contains(query.toString())
            }
        }
    }

}