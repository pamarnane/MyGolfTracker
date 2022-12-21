package ie.marnane.mygolftracker.ui.round

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import timber.log.Timber

class RoundViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    private val round = MutableLiveData<GolfRoundModel>()

    fun addGolfRound(firebaseUser: MutableLiveData<FirebaseUser>, golfRound: GolfRoundModel) {
        status.value = try {
           // GolfTrackerManager.create(golfRound)
            FirebaseDBManager.create(firebaseUser, golfRound)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getRound(userid:String, id: String) {
        try {
            //RoundManager.findById(email, id, Round)
            FirebaseDBManager.findById(userid, id, round)
            Timber.i("Detail getRound() Success : ${
                round.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getRound() Error : $e.message")
        }
    }

    fun updateRound(userid:String, id: String,round: GolfRoundModel) {
        try {
            //RoundManager.update(email, id, Round)
            FirebaseDBManager.update(userid, id, round)
            Timber.i("Detail update() Success : $round")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}