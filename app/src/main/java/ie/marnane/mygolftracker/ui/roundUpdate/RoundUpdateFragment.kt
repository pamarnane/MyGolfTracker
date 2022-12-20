package ie.marnane.mygolftracker.ui.roundUpdate

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.marnane.mygolftracker.databinding.FragmentRoundUpdateBinding
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import ie.marnane.mygolftracker.ui.round.RoundFragmentArgs
import ie.marnane.mygolftracker.ui.roundUpdate.RoundUpdateViewModel
import timber.log.Timber
import java.util.*

class RoundUpdateFragment : Fragment() {
    private var _binding: FragmentRoundUpdateBinding? = null
    private lateinit var roundUpdateViewModel: RoundUpdateViewModel

    var cal = Calendar.getInstance()
    //var golfRound = GolfRoundModel()

    val golfCourses = GolfTrackerManager.findAllCourses()
    val golfCourseList = mutableListOf<String>()

    private val layout get() = _binding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val args by navArgs<RoundUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRoundUpdateBinding.inflate(inflater, container, false)
        val root: View = layout.root

        roundUpdateViewModel = ViewModelProvider(this).get(RoundUpdateViewModel::class.java)
        roundUpdateViewModel.observableRound.observe(viewLifecycleOwner, androidx.lifecycle.Observer { render() })

        /*layout.btnUpdate.setOnClickListener {
            roundUpdateViewModel.updateRound(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.roundId, layout.round?.observableRound!!.value!!)
            findNavController().navigateUp()
        }*/

        setupMenu()

        // ****** Set Score picker Min/Max ****** //
        setNumberPickersMinMax()
        // ****** Set add button listener ****** //
        setUpdateButtonListener(layout)

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(ie.marnane.mygolftracker.R.menu.menu_round, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render() {

        layout.round = roundUpdateViewModel
        layout.hole1.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole1")!!
        layout.hole2.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole2")!!
        layout.hole3.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole3")!!
        layout.hole4.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole4")!!
        layout.hole5.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole5")!!
        layout.hole6.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole6")!!
        layout.hole7.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole7")!!
        layout.hole8.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole8")!!
        layout.hole9.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole9")!!
        layout.hole10.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole10")!!
        layout.hole11.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole11")!!
        layout.hole12.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole12")!!
        layout.hole13.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole13")!!
        layout.hole14.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole14")!!
        layout.hole15.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole15")!!
        layout.hole16.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole16")!!
        layout.hole17.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole17")!!
        layout.hole18.value = roundUpdateViewModel.observableRound.value?.scores?.get("hole18")!!
        Timber.i("Retrofit layout.round == $layout.round")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        roundUpdateViewModel.getRound(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.roundId)
    }

    fun setUpdateButtonListener(layout: FragmentRoundUpdateBinding) {
        layout.btnUpdate.setOnClickListener{
            println("Add Button Click")



            val scoreMap = hashMapOf<String, Int>(
                "hole1" to layout.hole1.value,
                "hole2" to layout.hole2.value,
                "hole3" to layout.hole3.value,
                "hole4" to layout.hole4.value,
                "hole5" to layout.hole5.value,
                "hole6" to layout.hole6.value,
                "hole7" to layout.hole7.value,
                "hole8" to layout.hole8.value,
                "hole9" to layout.hole9.value,
                "hole10" to layout.hole10.value,
                "hole11" to layout.hole11.value,
                "hole12" to layout.hole12.value,
                "hole13" to layout.hole13.value,
                "hole14" to layout.hole14.value,
                "hole15" to layout.hole15.value,
                "hole16" to layout.hole16.value,
                "hole17" to layout.hole17.value,
                "hole18" to layout.hole18.value
            )

            //golfRound.scores = scoreMap
            layout.round?.observableRound!!.value!!.scores = scoreMap

            roundUpdateViewModel.updateRound(loggedInViewModel.liveFirebaseUser.value?.uid!!, args.roundId, layout.round?.observableRound!!.value!!)

            findNavController().popBackStack()
        }
    }


    private fun setNumberPickersMinMax() {
        layout.hole1.minValue = 0
        layout.hole1.maxValue = 10
        layout.hole2.minValue = 0
        layout.hole2.maxValue = 10
        layout.hole3.minValue = 0
        layout.hole3.maxValue = 10
        layout.hole4.minValue = 0
        layout.hole4.maxValue = 10
        layout.hole5.minValue = 0
        layout.hole5.maxValue = 10
        layout.hole6.minValue = 0
        layout.hole6.maxValue = 10
        layout.hole7.minValue = 0
        layout.hole7.maxValue = 10
        layout.hole8.minValue = 0
        layout.hole8.maxValue = 10
        layout.hole9.minValue = 0
        layout.hole9.maxValue = 10
        layout.hole10.minValue = 0
        layout.hole10.maxValue = 10
        layout.hole11.minValue = 0
        layout.hole11.maxValue = 10
        layout.hole12.minValue = 0
        layout.hole12.maxValue = 10
        layout.hole13.minValue = 0
        layout.hole13.maxValue = 10
        layout.hole14.minValue = 0
        layout.hole14.maxValue = 10
        layout.hole15.minValue = 0
        layout.hole15.maxValue = 10
        layout.hole16.minValue = 0
        layout.hole16.maxValue = 10
        layout.hole17.minValue = 0
        layout.hole17.maxValue = 10
        layout.hole18.minValue = 0
        layout.hole18.maxValue = 10
    }
}