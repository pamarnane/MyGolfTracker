package ie.marnane.mygolftracker.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.firebase.FirebaseDBManager

object GolfTrackerManager : GolfTrackerStore {
    private val golfCourses = ArrayList<GolfCourseModel>()

    override fun findAll(userId: String, roundsList: MutableLiveData<List<GolfRoundModel>>) {
        TODO("Not yet implemented")
    }

    override fun findById(userId: String, roundId: String, round: MutableLiveData<GolfRoundModel>) {
        TODO("Not yet implemented")
    }

    override fun findAllwImages(roundsList: MutableLiveData<List<GolfRoundModel>>) {
        TODO("Not yet implemented")
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, round: GolfRoundModel) {
        TODO("Not yet implemented")
    }

    override fun update(userId: String, roundId: String, golfRound: GolfRoundModel) {
        TODO("Not yet implemented")
    }

    override fun delete(userId: String, roundId: String) {
        TODO("Not yet implemented")
    }

    override fun createCourse(
        firebaseUser: MutableLiveData<FirebaseUser>,
        golfCourse: GolfCourseModel,
    ) {
        TODO("Not yet implemented")
    }

    fun createCourse(golfCourse: GolfCourseModel) {
        golfCourses.add(golfCourse)
    }

    override fun incCourseRoundsPlayed(golfCourse: GolfCourseModel) {
        val foundGolfCourse: GolfCourseModel? = golfCourses.find { p -> p.uid == golfCourse.uid }
        if (foundGolfCourse != null) {
            foundGolfCourse.roundsPlayed += 1

        }
    }

    override fun decCourseRoundsPlayed(golfCourseName: String) {
        val foundGolfCourse: GolfCourseModel? = golfCourses.find { p -> p.title == golfCourseName }
        if (foundGolfCourse != null) {
            foundGolfCourse.roundsPlayed -= 1

        }
    }

    override fun findAllCourses(): List<GolfCourseModel> {
        return golfCourses
    }

    override fun findAllCourses(coursesList: MutableLiveData<List<GolfCourseModel>>) {
        coursesList.value = golfCourses
    }




}