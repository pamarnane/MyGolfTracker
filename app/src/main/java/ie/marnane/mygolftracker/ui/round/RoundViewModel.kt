package ie.marnane.mygolftracker.ui.round

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerManager

class RoundViewModel : ViewModel() {

/*    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text*/

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addGolfRound(golfRound: GolfRoundModel) {
        status.value = try {
            GolfTrackerManager.create(golfRound)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}