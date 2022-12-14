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
                          var scores : HashMap<String, Int> = hashMapOf<String, Int>(),
                          var image: String = "",
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
            "image" to image,
            "email" to email
        )
    }
}


@Parcelize
data class GolfCourseModel(var uid: String? = "",
                           var title: String = "",
                           var roundsPlayed: Int = 0,
                           var lat: Double = 0.0,
                           var lng: Double = 0.0,
                           var zoom: Float = 0f,
                           var icon: String = "") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "roundsPlayed" to roundsPlayed,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom,
            "icon" to icon
        )
    }
}