package org.wit.mygolftracker.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.mygolftracker.helpers.*
import timber.log.Timber
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "mygolftracker.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<GolfRoundModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GolfTrackerJSONStore(private val context: Context) : GolfTrackerStore {
    var golfRounds = mutableListOf<GolfRoundModel>()

    init {
        if (exists(context, JSON_FILE)) {
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
        var foundGolfRound: GolfRoundModel? = golfRounds.find { p -> p.id == golfRound.id }
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
        var foundGolfRound: GolfRoundModel? = golfRounds.find { p -> p.id == golfRound.id }
        if (foundGolfRound != null) {
            golfRounds.remove(foundGolfRound)
            logAll()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(golfRounds, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        golfRounds = gsonBuilder.fromJson(jsonString, listType)
    }


    private fun logAll() {
        golfRounds.forEach { Timber.i("$it") }
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