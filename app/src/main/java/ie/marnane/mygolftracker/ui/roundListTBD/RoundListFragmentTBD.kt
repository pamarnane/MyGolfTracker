package ie.marnane.mygolftracker.ui.roundListTBD

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.adapters.GolfTrackerAdapter
import ie.marnane.mygolftracker.adapters.GolfTrackerListener
import ie.marnane.mygolftracker.databinding.FragmentRoundListBinding
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.utils.createLoader


class RoundListFragmentTBD : Fragment()//, GolfTrackerListener
 {

   /* private var _binding: FragmentRoundListBinding? = null
    private lateinit var roundListViewModelTBD: RoundListViewModelTBD
    lateinit var loader : AlertDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        roundListViewModelTBD =
            ViewModelProvider(this).get(roundListViewModelTBD::class.java)

        _binding = FragmentRoundListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        roundListViewModelTBD.observableRoundsList.observe(viewLifecycleOwner, Observer {
                rounds ->
            rounds?.let { render(rounds as ArrayList<GolfRoundModel>) }
        })
        loader = createLoader(requireActivity())

*//*        binding.addFAB.setOnClickListener {
            findNavController().navigate(R.id.action_nav_round_list_to_nav_round);
        }*//*

*//*        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Donation")
                val adapter = binding.recyclerView.adapter as GolfTrackerAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                roundListViewModel.delete(roundListViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as DonationModel).uid!!)

                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onDonationClick(viewHolder.itemView.tag as DonationModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)*//*

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
        roundListViewModelTBD.load()
    }

*/
}