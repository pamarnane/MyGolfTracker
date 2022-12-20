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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.adapters.GolfTrackerAdapter
import ie.marnane.mygolftracker.adapters.GolfTrackerListener
import ie.marnane.mygolftracker.databinding.FragmentRoundListBinding
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import ie.marnane.mygolftracker.ui.round.RoundFragment
import ie.marnane.mygolftracker.ui.round.RoundFragmentDirections
import ie.marnane.mygolftracker.ui.roundListTBD.RoundListViewModel
import ie.marnane.mygolftracker.utils.*


class RoundListFragment : Fragment(), GolfTrackerListener {

    private var _binding: FragmentRoundListBinding? = null
    private lateinit var roundListViewModel: RoundListViewModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    lateinit var loader : AlertDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        roundListViewModel =
            ViewModelProvider(this).get(RoundListViewModel::class.java)
        _binding = FragmentRoundListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val root: View = binding.root

        loader = createLoader(requireActivity())

        roundListViewModel.observableRoundsList.observe(viewLifecycleOwner, Observer {
                rounds ->
            rounds?.let { render(rounds as ArrayList<GolfRoundModel>)
            checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Round")
                val adapter = binding.recyclerView.adapter as GolfTrackerAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                roundListViewModel.delete(roundListViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as GolfRoundModel).uid!!)

                hideLoader(loader)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(binding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onGolfRoundClick(viewHolder.itemView.tag as GolfRoundModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(binding.recyclerView)

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
            // binding.roundsNotFound.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            // binding.roundsNotFound.visibility = View.GONE
        }
    }

    override fun onGolfRoundClick(golfRound: GolfRoundModel) {
        val action = RoundListFragmentDirections.actionRoundListFragmentToRoundUpdateFragment(golfRound.uid!!)
        findNavController().navigate(action);
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Rounds")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                roundListViewModel.liveFirebaseUser.value = firebaseUser
                roundListViewModel.load()
            }
        })
        hideLoader(loader)
    }

    private fun setSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Rounds")
            roundListViewModel.load()
            hideLoader(loader)
        }
    }

    private fun checkSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing)
            binding.swiperefresh.isRefreshing = false
    }
}