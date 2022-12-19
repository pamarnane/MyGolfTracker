package ie.marnane.mygolftracker.ui.round

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.models.GolfTrackerStore

class RoundViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addGolfRound(firebaseUser: MutableLiveData<FirebaseUser>, golfRound: GolfRoundModel) {
        status.value = try {
           // GolfTrackerManager.create(golfRound)
            FirebaseDBManager.create(firebaseUser, golfRound)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}