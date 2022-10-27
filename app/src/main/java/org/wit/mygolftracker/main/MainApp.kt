package org.wit.mygolftracker.main

import android.app.Application
import org.wit.mygolftracker.models.GolfCourseModel
import org.wit.mygolftracker.models.GolfRoundModel
import org.wit.mygolftracker.models.GolfTrackerJSONStore
import org.wit.mygolftracker.models.GolfTrackerStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var golfRounds : GolfTrackerStore
    lateinit var golfCourses : GolfTrackerStore
    //val golfRounds = GolfTrackerJSONStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        golfRounds = GolfTrackerJSONStore(applicationContext)
        golfCourses = GolfTrackerJSONStore(applicationContext)
        i("MyGolfTracker started")

/*        var golfCourse = GolfCourseModel()

        golfCourse.title = "Ennis GC"
        golfCourse.lat = 52.84092016654442
        golfCourse.lng = -8.998135028815737
        golfCourses.createCourse(golfCourse.copy())

        golfCourse.title = "Dromoland Castle GC"
        golfCourse.lat = 52.78218837292427
        golfCourse.lng = -8.909143400919719
        golfCourses.createCourse(golfCourse.copy())*/

    }
}