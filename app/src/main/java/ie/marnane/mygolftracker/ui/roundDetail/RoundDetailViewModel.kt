package ie.marnane.mygolftracker.ui.roundList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.models.GolfRoundModel

class RoundDetailViewModel : ViewModel() {

    private val golfRounds =
        MutableLiveData<List<GolfRoundModel>>()

    val observableRoundsList: LiveData<List<GolfRoundModel>>
        get() = golfRounds

   // var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
/*        try {
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, donationsList)
            Timber.i("Report Load Success : ${donationsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }*/
        golfRounds.value = GolfTrackerManager.findAll()
    }

    fun delete(golfRound: GolfRoundModel) {
        /*val foundGolfRound: GolfRoundModel? = GolfTrackerManager { p -> p.id == golfRound.id }
        if (foundGolfRound != null) {
            golfRounds.remove(foundGolfRound)
            serialize()
            logAll()
        }*/
    }

    /*fun delete(userid: String, id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }*/
}