package org.wit.mygolftracker.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.mygolftracker.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val ROUND_JSON_FILE = "mygolftracker.json"
const val COURSE_JSON_FILE = "mygolftrackercourses.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<GolfRoundModel>>() {}.type
val listRoundType: Type = object : TypeToken<ArrayList<GolfCourseModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GolfTrackerJSONStore(private val context: Context) : GolfTrackerStore {
    var golfRounds = mutableListOf<GolfRoundModel>()
    var golfCourses = mutableListOf<GolfCourseModel>()

    init {
        if (exists(context, ROUND_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GolfRoundModel> {
        logAll()
        return golfRounds
    }


    override fun create(golfRound: GolfRoundModel) {
        golfRound.id = generateRandomId()
        golfRounds.add(golfRound)
        serialize()
    }

    override fun update(golfRound: GolfRoundModel) {
        val foundGolfRound: GolfRoundModel? = golfRounds.find { p -> p.id == golfRound.id }
        if (foundGolfRound != null) {
            foundGolfRound.course = golfRound.course
            foundGolfRound.date = golfRound.date
            for (i in golfRound.scores.indices) {
                foundGolfRound.scores[i] = golfRound.scores[i]
            }
            logAll()
        }
    }

    override fun delete(golfRound: GolfRoundModel) {
        val foundGolfRound: GolfRoundModel? = golfRounds.find { p -> p.id == golfRound.id }
        if (foundGolfRound != null) {
            golfRounds.remove(foundGolfRound)
            logAll()
        }
    }

    private fun logAll() {
        golfRounds.forEach { Timber.i("$it") }
    }



    // Course model functions
    override fun createCourse(golfCourse: GolfCourseModel) {
        golfCourse.id = generateRandomId()
        golfCourses.add(golfCourse)
        serializeCourse()
    }

    override fun findAllCourses(): MutableList<GolfCourseModel> {
        return golfCourses
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(golfRounds, listType)
        write(context, ROUND_JSON_FILE, jsonString)
    }

    private fun serializeCourse() {
        val jsonString = gsonBuilder.toJson(golfCourses, listRoundType)
        write(context, COURSE_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, ROUND_JSON_FILE)
        golfRounds = gsonBuilder.fromJson(jsonString, listType)
    }

}


class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}