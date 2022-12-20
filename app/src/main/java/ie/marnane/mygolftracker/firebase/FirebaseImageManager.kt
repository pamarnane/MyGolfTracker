package ie.marnane.mygolftracker.firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.UUID

object FirebaseImageManager {
    var storage = FirebaseStorage.getInstance().reference
    var imageUri = MutableLiveData<Uri>()

    fun uploadImageToFirebase(userid: String, bitmap: Bitmap, updating : Boolean) {
        // Get the data from an ImageView as bytes
        //val imageRef = storage.child("photos").child("${userid}.jpg")
        val imageRef = storage.child("photos").child("${userid}").child("${UUID.randomUUID()}.jpg")
        //val bitmap = (imageView as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        lateinit var uploadTask: UploadTask

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        imageRef.metadata.addOnSuccessListener { //File Exists
            if(updating) // Update existing Image
            {
                uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener { ut ->
                    ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                        imageUri.value = task.result!!
                        var test = imageUri.value
                    }
                }
            }

        }.addOnFailureListener { //File Doesn't Exist
            uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener { ut ->
                ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                    imageUri.value = task.result!!
                }
            }
        }

    }

    fun updateRoundImage(userid: String, imageUri : Uri?,// imageView: ImageView,
                         updating : Boolean) {
        var fbUri :Uri? = Uri.EMPTY
        Picasso.get().load(imageUri)
            .resize(200, 200)
            //.transform(customTransformation())
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerCrop()
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?,
                                            from: Picasso.LoadedFrom?
                ) {
                    Timber.i("DX onBitmapLoaded $bitmap")
                    uploadImageToFirebase(userid, bitmap!!,updating)
                    //imageView.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: java.lang.Exception?,
                                            errorDrawable: Drawable?) {
                    Timber.i("DX onBitmapFailed $e")
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }

    fun checkStorageForUserPic(userid: String) {
        val imageRef = storage.child("photos").child("${userid}.jpg")

        imageRef.metadata.addOnSuccessListener { //File Exists
            imageRef.downloadUrl.addOnCompleteListener { task ->
                imageUri.value = task.result!!
            }
            //File Doesn't Exist
        }.addOnFailureListener {
            imageUri.value = Uri.EMPTY
        }
    }

}