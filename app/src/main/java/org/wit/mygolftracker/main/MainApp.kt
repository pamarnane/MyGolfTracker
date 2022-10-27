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

        golfCourse.title = "Dromoland Castle GC"

        golfCourses.createCourse(golfCourse)*/

    }
}