package ie.marnane.mygolftracker.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.marnane.mygolftracker.models.GolfRoundModel

class GalleryViewModel : ViewModel() {

    private val golfRounds =
        MutableLiveData<List<GolfRoundModel>>()

    val observableImages: LiveData<List<GolfRoundModel>>
        get() = golfRounds

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}