package ie.marnane.mygolftracker.ui.gallery

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.firebase.FirebaseImageManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import timber.log.Timber

class GalleryViewModel : ViewModel() {

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    private val golfRounds =
        MutableLiveData<List<GolfRoundModel>>()

    var observablegolfRounds: LiveData<List<GolfRoundModel>>
        get() = golfRounds
        set(value) {golfRounds.value = value.value}

    init { load() }

    fun load() {
        try {
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, golfRounds)
            //FirebaseImageManager.checkStorageForUserPic(liveFirebaseUser.value?.uid!!)

/*            var golfRound : GolfRoundModel = GolfRoundModel()
            golfRound.course = "Test"
            golfRound.date = "20/12/2022"
            golfRound.image = "https://firebasestorage.googleapis.com/v0/b/mygolftracker-e148e.appspot.com/o/photos%2FdheKeSMWD1YODA5bp0VufWnP0Km1.jpg?alt=media&token=b01b694b-c756-4504-99f8-936231e02c86"

            val localList = ArrayList<GolfRoundModel>()
            localList.add(golfRound)
            golfRounds.value = localList*/

            Timber.i("Report Load Success : ${golfRounds.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }

    }
}