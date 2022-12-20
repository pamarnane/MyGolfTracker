package ie.marnane.mygolftracker.ui.roundUpdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import timber.log.Timber

class RoundUpdateViewModel : ViewModel() {

    private val round = MutableLiveData<GolfRoundModel>()

    var observableRound: LiveData<GolfRoundModel>
        get() = round
        set(value) {round.value = value.value}


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