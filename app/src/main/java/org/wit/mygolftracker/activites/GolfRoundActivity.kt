package org.wit.mygolftracker.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.mygolftracker.R
import org.wit.mygolftracker.databinding.ActivityGolfRoundBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfRoundModel


class GolfRoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGolfRoundBinding
    var golfRound = GolfRoundModel()
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGolfRoundBinding.inflate(layoutInflater)

        setContentView(binding.root)

      //  app = application as MainApp
    }
}