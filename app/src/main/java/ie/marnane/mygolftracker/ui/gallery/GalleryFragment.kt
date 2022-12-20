package ie.marnane.mygolftracker.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ie.marnane.mygolftracker.adapters.GolfTrackerAdapter
import ie.marnane.mygolftracker.adapters.GolfTrackerListener
import ie.marnane.mygolftracker.adapters.ImageGalleryAdapter
import ie.marnane.mygolftracker.adapters.ImageGalleryListener
import ie.marnane.mygolftracker.databinding.FragmentGalleryBinding
import ie.marnane.mygolftracker.firebase.FirebaseImageManager
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import ie.marnane.mygolftracker.ui.roundListTBD.RoundListViewModel
import ie.marnane.mygolftracker.utils.hideLoader
import ie.marnane.mygolftracker.utils.readImageUri
import ie.marnane.mygolftracker.utils.showLoader
import timber.log.Timber

class GalleryFragment : Fragment(), ImageGalleryListener {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 3)

        galleryViewModel.observablegolfRounds.observe(viewLifecycleOwner, Observer {
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
        var test = FirebaseImageManager.imageUri.value
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

    override fun onResume() {
        super.onResume()

        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                galleryViewModel.liveFirebaseUser.value = firebaseUser
                galleryViewModel.load()
            }
        })
    }

}