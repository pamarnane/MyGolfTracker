package ie.marnane.mygolftracker.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import timber.log.Timber


class MapsViewModel : ViewModel() {

    private var golfRounds =
        MutableLiveData<List<GolfRoundModel>>()

    val observableRoundsList: LiveData<List<GolfRoundModel>>
        get() = golfRounds

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()


    init { load() }

    fun load() {
        try {
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, golfRounds)
            Timber.i("Report Load Success : ${golfRounds.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun getAllCourses(uid: String): LiveData<List<GolfRoundModel>> {
        FirebaseDBManager.findAll(uid, golfRounds)
        return observableRoundsList
    }

    fun getAllCourses(): LiveData<List<GolfRoundModel>> {
        FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, golfRounds)
        return observableRoundsList
    }


}