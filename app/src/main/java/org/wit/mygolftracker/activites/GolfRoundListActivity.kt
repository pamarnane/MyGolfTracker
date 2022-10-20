package org.wit.mygolftracker.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.mygolftracker.R
import org.wit.mygolftracker.adapters.GolfTrackerAdapter
import org.wit.mygolftracker.adapters.GolfTrackerListener
import org.wit.mygolftracker.databinding.ActivityGolfRoundListBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfRoundModel

class GolfRoundListActivity : AppCompatActivity(), GolfTrackerListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGolfRoundListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGolfRoundListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = GolfTrackerAdapter(app.golfRounds.findAll(),this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, GolfRoundActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGolfRoundClick(golfRound: GolfRoundModel) {
        val launcherIntent = Intent(this, GolfRoundActivity::class.java)
        launcherIntent.putExtra("golfRound_edit", golfRound)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}