package org.wit.mygolftracker.models

import timber.log.Timber.i

class GolfTrackerJSONStore : GolfTrackerStore {
    val golfRounds = ArrayList<GolfRoundModel>()

    override fun findAll(): List<GolfRoundModel> {
        return golfRounds
    }

    override fun create(golfRound: GolfRoundModel) {
      //  placemark.id = getId()
        golfRounds.add(golfRound)
        logAll()
    }

    override fun update(golfRound: GolfRoundModel) {
        var foundGolfRound: GolfRoundModel? = golfRounds.find { p -> p.id == golfRound.id }
        if (foundGolfRound != null) {
            foundGolfRound.course = golfRound.course
            foundGolfRound.date = golfRound.date
            logAll()
        }
    }

    private fun logAll() {
        golfRounds.forEach { i("$it") }
    }
}