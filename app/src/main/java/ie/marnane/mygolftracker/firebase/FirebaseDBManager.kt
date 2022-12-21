package ie.marnane.mygolftracker.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.marnane.mygolftracker.models.GolfCourseModel
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerStore
import timber.log.Timber

object FirebaseDBManager : GolfTrackerStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(userid: String, roundsList: MutableLiveData<List<GolfRoundModel>>) {

        database.child("user-rounds").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Golf Round error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<GolfRoundModel>()
                    val children = snapshot.children
                    children.forEach {
                        val round = it.getValue(GolfRoundModel::class.java)
                        localList.add(round!!)
                    }
                    database.child("user-rounds").child(userid)
                        .removeEventListener(this)

                    roundsList.value = localList
                }
            })
    }

    override fun findById(
        userId: String,
        roundId: String,
        round: MutableLiveData<GolfRoundModel>,
    ) {
        database.child("user-rounds").child(userId)
            .child(roundId).get().addOnSuccessListener {
                round.value = it.getValue(GolfRoundModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }


    override fun findAllwImages(roundsList: MutableLiveData<List<GolfRoundModel>>) {
        TODO("Not yet implemented")
    }


    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, round: GolfRoundModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("rounds").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        round.uid = key
        val roundDetails = round.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/rounds/$key"] = roundDetails
        childAdd["/user-rounds/$uid/$key"] = roundDetails

        database.updateChildren(childAdd)
    }

    override fun createCourse(firebaseUser: MutableLiveData<FirebaseUser>, golfCourse: GolfCourseModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("courses").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        golfCourse.uid = key
        val roundDetails = golfCourse.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/courses/$key"] = roundDetails

        database.updateChildren(childAdd)
    }

    override fun incCourseRoundsPlayed(golfCourse: GolfCourseModel) {
        TODO("Not yet implemented")
    }

    override fun decCourseRoundsPlayed(golfCourseName: String) {
        TODO("Not yet implemented")
    }

    override fun findAllCourses(): List<GolfCourseModel> {
        TODO("Not yet implemented")
    }

    override fun findAllCourses(coursesList: MutableLiveData<List<GolfCourseModel>>) {
        database.child("courses")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Golf Round error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<GolfCourseModel>()
                    val children = snapshot.children
                    children.forEach {
                        val course = it.getValue(GolfCourseModel::class.java)
                        localList.add(course!!)
                    }
                    database.child("courses")
                        .removeEventListener(this)

                    coursesList.value = localList
                }
            })
    }


    override fun update(userid: String, roundId: String, round: GolfRoundModel) {

        val roundDetails = round.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["rounds/$roundId"] = roundDetails
        childUpdate["user-rounds/$userid/$roundId"] = roundDetails

        database.updateChildren(childUpdate)
    }

    override fun delete(userId: String, roundId: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/rounds/$roundId"] = null
        childDelete["/user-rounds/$userId/$roundId"] = null

        database.updateChildren(childDelete)
    }
}