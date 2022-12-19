package ie.marnane.mygolftracker.ui.roundList

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.adapters.GolfTrackerAdapter
import ie.marnane.mygolftracker.adapters.GolfTrackerListener
import ie.marnane.mygolftracker.databinding.FragmentRoundListBinding
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import ie.marnane.mygolftracker.ui.roundListTBD.RoundListViewModel
import ie.marnane.mygolftracker.utils.*


class RoundListFragment : Fragment(), GolfTrackerListener {

    private var _binding: FragmentRoundListBinding? = null
    private lateinit var roundListViewModel: RoundListViewModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    lateinit var loader : AlertDialog
    //var golfRound = GolfRoundModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        roundListViewModel =
            ViewModelProvider(this).get(RoundListViewModel::class.java)

        _binding = FragmentRoundListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loader = createLoader(requireActivity())

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        roundListViewModel.observableRoundsList.observe(viewLifecycleOwner, Observer {
                rounds ->
            rounds?.let { render(rounds as ArrayList<GolfRoundModel>) }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(golfRoundsList: ArrayList<GolfRoundModel>) {
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

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Donations")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                roundListViewModel.liveFirebaseUser.value = firebaseUser
                roundListViewModel.load()
            }
        })
        hideLoader(loader)
    }

}