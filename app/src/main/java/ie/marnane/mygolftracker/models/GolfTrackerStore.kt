package ie.marnane.mygolftracker.models

interface GolfTrackerStore {
    fun findAll(): List<GolfRoundModel>
    fun findAllwImages(): List<GolfRoundModel>
    fun create(golfRound: GolfRoundModel)
    fun update(golfRound: GolfRoundModel)
    fun delete(golfRound: GolfRoundModel)

    fun createCourse(golfCourse: GolfCourseModel)
    fun incCourseRoundsPlayed(golfCourse: GolfCourseModel)
    fun decCourseRoundsPlayed(golfCourseName: String)
    fun findAllCourses(): List<GolfCourseModel>


}