package org.wit.mygolftracker.main

import android.app.Application
import org.wit.mygolftracker.models.GolfTrackerJSONStore
import org.wit.mygolftracker.models.GolfRoundModel
import org.wit.mygolftracker.models.GolfTrackerStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var golfRounds : GolfTrackerStore
    //val golfRounds = GolfTrackerJSONStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        golfRounds = GolfTrackerJSONStore(applicationContext)
        i("MyGolfTracker started")

    }
}