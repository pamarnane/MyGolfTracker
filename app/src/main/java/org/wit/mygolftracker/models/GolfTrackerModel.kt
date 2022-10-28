package org.wit.mygolftracker.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class GolfRoundModel(var id: Long = 0,
                          var course: String = "",
                          var date: String = "",
                          var comment: String = "",
                          var scores: Array<Int> = Array(18){0},
                          var image: Uri = Uri.EMPTY) : Parcelable

@Parcelize
data class GolfCourseModel(var id: Long = 0,
                           var title: String = "",
                           var roundsPlayed: Int = 0,
                           var lat: Double = 0.0,
                           var lng: Double = 0.0,
                           var zoom: Float = 0f) : Parcelable


