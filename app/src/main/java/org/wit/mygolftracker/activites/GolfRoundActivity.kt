package org.wit.mygolftracker.activites

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import org.wit.mygolftracker.R
import org.wit.mygolftracker.databinding.ActivityGolfRoundBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfRoundModel
import java.text.SimpleDateFormat
import java.util.*


class GolfRoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGolfRoundBinding
    var golfRound = GolfRoundModel()
    lateinit var app: MainApp
    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGolfRoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        binding.roundDate.setOnClickListener() {
            DatePickerDialog(this@GolfRoundActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnAdd.setOnClickListener() {
            golfRound.course = binding.roundCourse.text.toString()
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

            app.golfRounds.create(golfRound.copy())
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


}