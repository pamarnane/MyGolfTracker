package org.wit.mygolftracker.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import org.wit.mygolftracker.R
import org.wit.mygolftracker.databinding.ActivityGolfRoundBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfCourseModel
import org.wit.mygolftracker.models.GolfRoundModel
import timber.log.Timber.i
import java.util.*


class GolfRoundActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityGolfRoundBinding
    var golfRound = GolfRoundModel()
    lateinit var app: MainApp
    var cal = Calendar.getInstance()
    var edit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGolfRoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)*/

        app = application as MainApp

        setNumberPickersMinMax()

        val golfCourses = app.golfRounds.findAllCourses()
        val golfCourseList = mutableListOf<String>()
        golfCourses.forEach { it ->
            golfCourseList.add(it.title)
        }
        golfCourseList.sort()

        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.spinner_list, golfCourseList
        )
        binding.spinnerCourse.adapter = arrayAdapter
        binding.spinnerCourse.onItemSelectedListener = this

        if(intent.hasExtra("golfRound_edit")){
            i("Data passed for edit")
            edit = true
            golfRound = intent.extras?.getParcelable("golfRound_edit")!!

            //val golfCourseInt = golfCourseList.indexOf(golfRound.course)

            binding.spinnerCourse.setSelection(golfCourseList.indexOf(golfRound.course))
            binding.roundDate.setText(golfRound.date)

            binding.hole1.setOnValueChangedListener { _, _, newVal ->
                //Display the newly selected number to paymentAmount
                binding.hole1.value = newVal
            }


            binding.hole1.value = golfRound.scores[0]
            binding.hole2.value = golfRound.scores[1]
            binding.hole3.value = golfRound.scores[2]
            binding.hole4.value = golfRound.scores[3]
            binding.hole5.value = golfRound.scores[4]
            binding.hole6.value = golfRound.scores[5]
            binding.hole7.value = golfRound.scores[6]
            binding.hole8.value = golfRound.scores[7]
            binding.hole9.value = golfRound.scores[8]
            binding.hole10.value = golfRound.scores[9]
            binding.hole11.value = golfRound.scores[10]
            binding.hole12.value = golfRound.scores[11]
            binding.hole13.value = golfRound.scores[12]
            binding.hole14.value = golfRound.scores[13]
            binding.hole15.value = golfRound.scores[14]
            binding.hole16.value = golfRound.scores[15]
            binding.hole17.value = golfRound.scores[16]
            binding.hole18.value = golfRound.scores[17]


            binding.btnAdd.setText(R.string.save_round)
        }

        binding.roundDate.setOnClickListener() {
            DatePickerDialog(this@GolfRoundActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnAdd.setOnClickListener() {
            //golfRound.course = binding.roundCourse.text.toString()
            golfRound.date = binding.roundDate.text.toString()
            golfRound.scores[0] = binding.hole1.value
            golfRound.scores[1] = binding.hole2.value
            golfRound.scores[2] = binding.hole3.value
            golfRound.scores[3] = binding.hole4.value
            golfRound.scores[4] = binding.hole5.value
            golfRound.scores[5] = binding.hole6.value
            golfRound.scores[6] = binding.hole7.value
            golfRound.scores[7] = binding.hole8.value
            golfRound.scores[8] = binding.hole9.value
            golfRound.scores[9] = binding.hole10.value
            golfRound.scores[10] = binding.hole11.value
            golfRound.scores[11] = binding.hole12.value
            golfRound.scores[12] = binding.hole13.value
            golfRound.scores[13] = binding.hole14.value
            golfRound.scores[14] = binding.hole15.value
            golfRound.scores[15] = binding.hole16.value
            golfRound.scores[16] = binding.hole17.value
            golfRound.scores[17] = binding.hole18.value


            if (edit) {
               app.golfRounds.update(golfRound.copy())
            } else {
                app.golfRounds.create(golfRound.copy())
                val foundGolfCourse: GolfCourseModel? = golfCourses.find { p -> p.title == golfRound.course }
                if (foundGolfCourse != null) {
                    app.golfCourses.incCourseRoundsPlayed(foundGolfCourse)
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    // create an OnDateSetListener
    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        var cal = Calendar.getInstance()
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val updatedMonth = monthOfYear + 1
            val myFormat = (dayOfMonth.toString() + "/" + updatedMonth.toString() + "/" + year.toString())
            binding.roundDate.setText(myFormat)
        }
    }

    private fun setNumberPickersMinMax() {
        binding.hole1.minValue = 0
        binding.hole1.maxValue = 10
        binding.hole2.minValue = 0
        binding.hole2.maxValue = 10
        binding.hole3.minValue = 0
        binding.hole3.maxValue = 10
        binding.hole4.minValue = 0
        binding.hole4.maxValue = 10
        binding.hole5.minValue = 0
        binding.hole5.maxValue = 10
        binding.hole6.minValue = 0
        binding.hole6.maxValue = 10
        binding.hole7.minValue = 0
        binding.hole7.maxValue = 10
        binding.hole8.minValue = 0
        binding.hole8.maxValue = 10
        binding.hole9.minValue = 0
        binding.hole9.maxValue = 10
        binding.hole10.minValue = 0
        binding.hole10.maxValue = 10
        binding.hole11.minValue = 0
        binding.hole11.maxValue = 10
        binding.hole12.minValue = 0
        binding.hole12.maxValue = 10
        binding.hole13.minValue = 0
        binding.hole13.maxValue = 10
        binding.hole14.minValue = 0
        binding.hole14.maxValue = 10
        binding.hole15.minValue = 0
        binding.hole15.maxValue = 10
        binding.hole16.minValue = 0
        binding.hole16.maxValue = 10
        binding.hole17.minValue = 0
        binding.hole17.maxValue = 10
        binding.hole18.minValue = 0
        binding.hole18.maxValue = 10
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_golfround, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                val golfCourseName = golfRound.course
                app.golfRounds.delete(golfRound)
                app.golfCourses.decCourseRoundsPlayed(golfCourseName)

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var text: String = parent?.getItemAtPosition(position).toString()
        golfRound.course = text
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}