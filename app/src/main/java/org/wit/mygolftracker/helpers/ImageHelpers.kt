package org.wit.mygolftracker.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.wit.mygolftracker.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {

    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_round_image.toString())
    //chooseFile.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    intentLauncher.launch(chooseFile)
}
