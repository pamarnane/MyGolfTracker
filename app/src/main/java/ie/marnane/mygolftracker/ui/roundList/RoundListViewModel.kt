package ie.marnane.mygolftracker.ui.roundList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.models.GolfRoundModel

class RoundListViewModel : ViewModel() {

/*    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text*/


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