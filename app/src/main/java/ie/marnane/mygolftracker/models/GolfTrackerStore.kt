package ie.marnane.mygolftracker.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface GolfTrackerStore {

    //JSON Interface
/*    fun findAll(roundsList:MutableLiveData<List<GolfRoundModel>>)
    fun findAll(): List<GolfRoundModel>
    fun findAllwImages(): List<GolfRoundModel>
    fun create(golfRound: GolfRoundModel)
    fun update(golfRound: GolfRoundModel)
    fun delete(golfRound: GolfRoundModel)*/

    //Firebase Interface
    fun findAll(userId:String, roundsList:MutableLiveData<List<GolfRoundModel>>)
    fun findById(userId: String, roundId: String, round:MutableLiveData<GolfRoundModel>)
    fun findAllwImages(roundsList:MutableLiveData<List<GolfRoundModel>>)
    fun findAllwImages(userId: String, roundsList:MutableLiveData<List<GolfRoundModel>>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, round: GolfRoundModel)
    fun update(userId: String, roundId: String, golfRound: GolfRoundModel)
    fun delete(userId: String, roundId: String)

    fun createCourse(firebaseUser: MutableLiveData<FirebaseUser>, golfCourse: GolfCourseModel)
    fun incCourseRoundsPlayed(golfCourse: GolfCourseModel)
    fun decCourseRoundsPlayed(golfCourseName: String)
    fun findAllCourses(): List<GolfCourseModel>
    fun findAllCourses(coursesList:MutableLiveData<List<GolfCourseModel>>)
}