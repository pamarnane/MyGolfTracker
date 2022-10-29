package org.wit.mygolftracker.main

import android.app.Application
import android.content.Context
import org.wit.mygolftracker.helpers.exists
import org.wit.mygolftracker.models.*
import timber.log.Timber
import timber.log.Timber.i
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists

class MainApp : Application() {
    lateinit var golfRounds : GolfTrackerStore
    lateinit var golfCourses : GolfTrackerStore
    //val golfRounds = GolfTrackerJSONStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

       if (!Path("/data/data/org.wit.mygolftracker/files/mygolftrackercourses.json").exists()) {

           Path("/data/data/org.wit.mygolftracker/files/mygolftracker.json").deleteIfExists()

           val golfCourse = GolfCourseModel()

           golfCourse.title = "Ennis GC"
           golfCourse.lat = 52.84092016654442
           golfCourse.lng = -8.998135028815737
           golfCourse.icon = "ennis"
           golfCourses.createCourse(golfCourse.copy())

           golfCourse.title = "Dromoland Castle GC"
           golfCourse.lat = 52.78218837292427
           golfCourse.lng = -8.909143400919719
           golfCourse.icon = "dromoland"
           golfCourses.createCourse(golfCourse.copy())

           golfCourse.title = "Lahinch GC - Old"
           golfCourse.lat = 52.93535192772572
           golfCourse.lng = -9.345525869316168
           golfCourse.icon = "lahinch_old"
           golfCourses.createCourse(golfCourse.copy())

           golfCourse.title = "Lahinch GC - Castle"
           golfCourse.lat = 52.93535192772572
           golfCourse.lng = -9.345525869316168
           golfCourse.icon = "lahinch_castle"
           golfCourses.createCourse(golfCourse.copy())

           golfCourse.title = "Shannon GC"
           golfCourse.lat = 52.68938272732169
           golfCourse.lng = -8.937344286642613
           golfCourse.icon = "lahinch"
           golfCourses.createCourse(golfCourse.copy())
       }

        golfRounds = GolfTrackerJSONStore(applicationContext)
        golfCourses = GolfTrackerJSONStore(applicationContext)
        i("MyGolfTracker started")
    }
}