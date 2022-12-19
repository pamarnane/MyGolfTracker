package ie.marnane.mygolftracker.ui.round

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.marnane.mygolftracker.R
import ie.marnane.mygolftracker.databinding.FragmentRoundBinding
import ie.marnane.mygolftracker.helpers.showImagePicker
import ie.marnane.mygolftracker.models.GolfRoundModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import java.util.*
import kotlin.collections.HashMap

class RoundFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentRoundBinding? = null
    private lateinit var roundViewModel: RoundViewModel

    var cal = Calendar.getInstance()
    var golfRound = GolfRoundModel()

    val golfCourses = GolfTrackerManager.findAllCourses()
    val golfCourseList = mutableListOf<String>()

    private val layout get() = _binding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        roundViewModel =
            ViewModelProvider(this).get(RoundViewModel::class.java)
      /*  roundViewModel.observableStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer { render() }
        )*/

        _binding = FragmentRoundBinding.inflate(inflater, container, false)
        val root: View = layout.root
        setupMenu()


        // *****
        layout.roundDate.setOnClickListener() {
            context?.let { it1 ->
                DatePickerDialog(it1,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }

        // ****** Populate golf course spinner ****** //
        /*val golfCourses = GolfTrackerManager.findAllCourses()
        val golfCourseList = mutableListOf<String>()*/
        golfCourses.forEach { it ->
            golfCourseList.add(it.title)
        }
        golfCourseList.sort()

        val arrayAdapter = context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_spinner_item, golfCourseList)
        }
        layout.spinnerCourse.adapter = arrayAdapter

        layout.spinnerCourse.onItemSelectedListener = this


        // ****** Set Score picker Min/Max ****** //
        setNumberPickersMinMax()
        // ****** Set add button listener ****** //
        setAddButtonListener(layout)

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_round, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
/*                when (menuItem.itemId) {
                    R.id.item_cancel -> {
                    }
                    R.id.item_delete -> {
                        *//*val golfCourseName = golfRound.course
                        app.golfRounds.delete(golfRound)
                        app.golfCourses.decCourseRoundsPlayed(golfCourseName)*//*
                    }
                    R.id.item_addImage -> {
                        showImagePicker(imageIntentLauncher)
                    }
                }
                return super.onOptionsItemSelected(item)*/



                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render() {
/*        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
        }*/

        layout.spinnerCourse.setSelection(golfCourseList.indexOf(golfRound.course))
        layout.roundDate.setText(golfRound.date)


/*        layout.hole1.value = golfRound.scores[0]
        layout.hole2.value = golfRound.scores[1]
        layout.hole3.value = golfRound.scores[2]
        layout.hole4.value = golfRound.scores[3]
        layout.hole5.value = golfRound.scores[4]
        layout.hole6.value = golfRound.scores[5]
        layout.hole7.value = golfRound.scores[6]
        layout.hole8.value = golfRound.scores[7]
        layout.hole9.value = golfRound.scores[8]
        layout.hole10.value = golfRound.scores[9]
        layout.hole11.value = golfRound.scores[10]
        layout.hole12.value = golfRound.scores[11]
        layout.hole13.value = golfRound.scores[12]
        layout.hole14.value = golfRound.scores[13]
        layout.hole15.value = golfRound.scores[14]
        layout.hole16.value = golfRound.scores[15]
        layout.hole17.value = golfRound.scores[16]
        layout.hole18.value = golfRound.scores[17]*/
    }


    // Create an OnDateSetListener
    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        var cal = Calendar.getInstance()
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val updatedMonth = monthOfYear + 1
            val myFormat = (dayOfMonth.toString() + "/" + updatedMonth.toString() + "/" + year.toString())
            layout.roundDate.setText(myFormat)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun setAddButtonListener(layout: FragmentRoundBinding) {
        layout.btnAdd.setOnClickListener{
            println("Add Button Click")
            //var golfRound = GolfRoundModel()
            //layout.spinnerCourse.setSelection(golfCourseList.indexOf(golfRound.course))
            golfRound.date = layout.roundDate.text.toString()
            golfRound.hole1 = layout.hole1.value
/*            golfRound.scores[0] = layout.hole1.value
            golfRound.scores[1] = layout.hole2.value
            golfRound.scores[2] = layout.hole3.value
            golfRound.scores[3] = layout.hole4.value
            golfRound.scores[4] = layout.hole5.value
            golfRound.scores[5] = layout.hole6.value
            golfRound.scores[6] = layout.hole7.value
            golfRound.scores[7] = layout.hole8.value
            golfRound.scores[8] = layout.hole9.value
            golfRound.scores[9] = layout.hole10.value
            golfRound.scores[10] = layout.hole11.value
            golfRound.scores[11] = layout.hole12.value
            golfRound.scores[12] = layout.hole13.value
            golfRound.scores[13] = layout.hole14.value
            golfRound.scores[14] = layout.hole15.value
            golfRound.scores[15] = layout.hole16.value
            golfRound.scores[16] = layout.hole17.value
            golfRound.scores[17] = layout.hole18.value*/

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

            golfRound.scores = scoreMap



            var validInput = false;

            if(golfRound.course ==  ""){
                Toast.makeText(context, getString(R.string.enter_course_name), Toast.LENGTH_LONG)
                    .show()
            }
            else if (golfRound.date ==  "") {
                Snackbar.make(it, R.string.enter_course_date, Snackbar.LENGTH_LONG)
                    .show()
            }
            else if (golfRound.course !=  "" && golfRound.date !=  "")  { validInput = true}

            if (validInput) {
                roundViewModel.addGolfRound(loggedInViewModel.liveFirebaseUser, golfRound)
            }
            findNavController().popBackStack()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var text: String = parent?.getItemAtPosition(position).toString()
        golfRound.course = text
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
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