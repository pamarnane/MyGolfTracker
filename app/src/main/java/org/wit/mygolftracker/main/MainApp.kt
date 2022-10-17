package org.wit.mygolftracker.main

import android.app.Application
import org.wit.mygolftracker.models.GolfTrackerJSONStore
import org.wit.mygolftracker.models.GolfRoundModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val golfRounds = GolfTrackerJSONStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("MyGolfTracker started")

    }
}