package ie.marnane.mygolftracker.ui.roundList

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


class RoundListFragment : Fragment(), GolfTrackerListener {

    private var _binding: FragmentRoundListBinding? = null
    private lateinit var roundDetailViewModel: RoundDetailViewModel

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

        binding.addFAB.setOnClickListener {
            findNavController().navigate(R.id.action_nav_round_list_to_nav_round);
        }

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

    override fun onResume() {
        super.onResume()
        roundDetailViewModel.load()
    }
}