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

        val golfCourses = app.golfRounds.findAllCourses()
        val golfCourseList = mutableListOf<String>()
        golfCourses.forEach { it ->
            golfCourseList.add(it.title)
        }
        golfCourseList.sort()

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, golfCourseList
        )
        binding.spinnerCourse.adapter = arrayAdapter
        binding.spinnerCourse.onItemSelectedListener = this

        if(intent.hasExtra("golfRound_edit")){
            i("Data passed for edit")
            edit = true
            golfRound = intent.extras?.getParcelable("golfRound_edit")!!

            //binding.roundCourse.setText(golfRound.course)
            binding.roundDate.setText(golfRound.date)

            binding.hole1.setText(golfRound.scores[0].toString())
            binding.hole2.setText(golfRound.scores[1].toString())
            binding.hole3.setText(golfRound.scores[2].toString())
            binding.hole4.setText(golfRound.scores[3].toString())
            binding.hole5.setText(golfRound.scores[4].toString())
            binding.hole6.setText(golfRound.scores[5].toString())
            binding.hole7.setText(golfRound.scores[6].toString())
            binding.hole8.setText(golfRound.scores[7].toString())
            binding.hole9.setText(golfRound.scores[8].toString())
            binding.hole10.setText(golfRound.scores[9].toString())
            binding.hole11.setText(golfRound.scores[10].toString())
            binding.hole12.setText(golfRound.scores[11].toString())
            binding.hole13.setText(golfRound.scores[12].toString())
            binding.hole14.setText(golfRound.scores[13].toString())
            binding.hole15.setText(golfRound.scores[14].toString())
            binding.hole16.setText(golfRound.scores[15].toString())
            binding.hole17.setText(golfRound.scores[16].toString())
            binding.hole18.setText(golfRound.scores[17].toString())

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
            golfRound.scores[0] = binding.hole1.text.toString().toInt()
            golfRound.scores[1] = binding.hole2.text.toString().toInt()
            golfRound.scores[2] = binding.hole3.text.toString().toInt()
            golfRound.scores[3] = binding.hole4.text.toString().toInt()
            golfRound.scores[4] = binding.hole5.text.toString().toInt()
            golfRound.scores[5] = binding.hole6.text.toString().toInt()
            golfRound.scores[6] = binding.hole7.text.toString().toInt()
            golfRound.scores[7] = binding.hole8.text.toString().toInt()
            golfRound.scores[8] = binding.hole9.text.toString().toInt()
            golfRound.scores[9] = binding.hole10.text.toString().toInt()
            golfRound.scores[10] = binding.hole11.text.toString().toInt()
            golfRound.scores[11] = binding.hole12.text.toString().toInt()
            golfRound.scores[12] = binding.hole13.text.toString().toInt()
            golfRound.scores[13] = binding.hole14.text.toString().toInt()
            golfRound.scores[14] = binding.hole15.text.toString().toInt()
            golfRound.scores[15] = binding.hole16.text.toString().toInt()
            golfRound.scores[16] = binding.hole17.text.toString().toInt()
            golfRound.scores[17] = binding.hole18.text.toString().toInt()

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