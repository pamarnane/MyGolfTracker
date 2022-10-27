package org.wit.mygolftracker.models

interface GolfTrackerStore {
    fun findAll(): List<GolfRoundModel>
    fun create(golfRound: GolfRoundModel)
    fun update(golfRound: GolfRoundModel)
    fun delete(golfRound: GolfRoundModel)

    fun createCourse(golfCourse: GolfCourseModel)
    fun updateCourseRoundsPlayed(golfCourse: GolfCourseModel)
    fun findAllCourses(): List<GolfCourseModel>

}