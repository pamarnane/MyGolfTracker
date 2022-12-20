package ie.marnane.mygolftracker.helpers

import android.net.Uri


class Converters {

    fun fromUri(value: Uri): String {
        return value.toString()
    }


    fun toUri(string: String): Uri {
        return Uri.parse(string)
    }
}