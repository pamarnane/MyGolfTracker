package ie.marnane.mygolftracker.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ie.marnane.mygolftracker.adapters.GolfTrackerAdapter
import ie.marnane.mygolftracker.adapters.GolfTrackerListener
import ie.marnane.mygolftracker.adapters.ImageGalleryAdapter
import ie.marnane.mygolftracker.adapters.ImageGalleryListener
import ie.marnane.mygolftracker.databinding.FragmentGalleryBinding
import ie.marnane.mygolftracker.models.GolfRoundModel

class GalleryFragment : Fragment(), ImageGalleryListener {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        galleryViewModel.observableImages.observe(viewLifecycleOwner, Observer {
                images ->
            images?.let { render(images) }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(golfRoundsList: List<GolfRoundModel>) {
        binding.recyclerView.adapter = ImageGalleryAdapter(golfRoundsList, this)
        if (golfRoundsList.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            //binding.donationsNotFound.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            // binding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onImageClick(golfRound: GolfRoundModel) {
        TODO("Not yet implemented")
    }

}