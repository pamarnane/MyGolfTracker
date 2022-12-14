package ie.marnane.mygolftracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.marnane.mygolftracker.databinding.ImagegalleryItemBinding
import ie.marnane.mygolftracker.firebase.FirebaseImageManager
import ie.marnane.mygolftracker.models.GolfRoundModel

interface ImageGalleryListener {
    fun onImageClick(golfRound: GolfRoundModel)
}

class ImageGalleryAdapter constructor(private var golfRounds: List<GolfRoundModel>,
                                   private val listener: ImageGalleryListener) :
    RecyclerView.Adapter<ImageGalleryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = ImagegalleryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val golfRound = golfRounds[holder.adapterPosition]
        holder.bind(golfRound, listener)
    }

    override fun getItemCount(): Int = golfRounds.size

    class MainHolder(private val binding: ImagegalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(golfRound: GolfRoundModel, listener: ImageGalleryListener) {
            binding.imageCourseTitle.text = golfRound.course
            if (golfRound.image != "") {
            Picasso.get()
                .load(golfRound.image)
                .into(binding.imageView)
            }
        }
    }
}