package ie.marnane.mygolftracker.ui.roundList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.adapters.GolfTrackerAdapter
import ie.marnane.mygolftracker.adapters.GolfTrackerListener
import ie.marnane.mygolftracker.databinding.FragmentRoundListBinding
import ie.marnane.mygolftracker.helpers.showImagePicker
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import timber.log.Timber


class RoundDetailFragment : Fragment(), GolfTrackerListener {

    private var _binding: FragmentRoundListBinding? = null
    private lateinit var roundDetailViewModel: RoundDetailViewModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var golfRound = GolfRoundModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        roundDetailViewModel =
            ViewModelProvider(this).get(RoundDetailViewModel::class.java)

        _binding = FragmentRoundListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        roundDetailViewModel.observableRoundsList.observe(viewLifecycleOwner, Observer {
                rounds ->
            rounds?.let { render(rounds) }
        })

/*        binding.addFAB.setOnClickListener {
            findNavController().navigate(R.id.action_nav_round_list_to_nav_round);
        }*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(golfRoundsList: List<GolfRoundModel>) {
        binding.recyclerView.adapter = GolfTrackerAdapter(golfRoundsList,this)
        if (golfRoundsList.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            //binding.donationsNotFound.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
           // binding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onGolfRoundClick(golfRound: GolfRoundModel) {
        findNavController().navigate(R.id.action_nav_round_list_to_nav_round);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
            }
            R.id.item_delete -> {
                /*val golfCourseName = golfRound.course
                app.golfRounds.delete(golfRound)
                app.golfCourses.decCourseRoundsPlayed(golfCourseName)*/
            }
            R.id.item_addImage -> {
                showImagePicker(imageIntentLauncher)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            golfRound.image = result.data!!.data!!
                            GolfTrackerManager.update(golfRound)
                            /*                         Picasso.get()
                                                         .load(golfRound.image)
                                                         .into(binding.golfRound)*/
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        roundDetailViewModel.load()
    }
}