package ie.marnane.mygolftracker.models

object GolfTrackerManager : GolfTrackerStore {

    private val golfRounds = ArrayList<GolfRoundModel>()
    private val golfCourses = ArrayList<GolfCourseModel>()

    override fun findAll(): List<GolfRoundModel> {
        return golfRounds
    }

    override fun findAllwImages(): List<GolfRoundModel> {
        TODO("Not yet implemented")
    }

    override fun create(golfRound: GolfRoundModel) {
        golfRounds.add(golfRound)
    }

    override fun update(golfRound: GolfRoundModel) {
        TODO("Not yet implemented")
    }

    override fun delete(golfRound: GolfRoundModel) {
        TODO("Not yet implemented")
    }

    override fun createCourse(golfCourse: GolfCourseModel) {
        golfCourses.add(golfCourse)
    }

    override fun incCourseRoundsPlayed(golfCourse: GolfCourseModel) {
        TODO("Not yet implemented")
    }

    override fun decCourseRoundsPlayed(golfCourseName: String) {
        TODO("Not yet implemented")
    }

    override fun findAllCourses(): List<GolfCourseModel> {
        return golfCourses
    }
}