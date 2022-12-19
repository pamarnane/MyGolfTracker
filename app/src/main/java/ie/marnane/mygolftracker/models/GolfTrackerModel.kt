package ie.marnane.mygolftracker.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.HashMap

@IgnoreExtraProperties
@Parcelize
data class GolfRoundModel(var uid: String? = "",
                          var course: String = "",
                          var date: String = "",
                          var comment: String = "",
                          //var scores: Array<Int> = Array(18){0},
                            var scores : HashMap<String, Int>,
                          //var image: Uri = Uri.EMPTY,
                          var email: String? = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "course" to course,
            "date" to date,
            "comment" to comment,
            "scores" to scores,
            //"image" to image,
            "email" to email
        )
    }
}

@Parcelize
data class GolfCourseModel(var id: Long = 0,
                           var title: String = "",
                           var roundsPlayed: Int = 0,
                           var lat: Double = 0.0,
                           var lng: Double = 0.0,
                           var zoom: Float = 0f,
                           var icon: String = "") : Parcelable