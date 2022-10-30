package org.wit.mygolftracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.mygolftracker.R
import org.wit.mygolftracker.adapters.GolfTrackerAdapter
import org.wit.mygolftracker.adapters.GolfTrackerListener
import org.wit.mygolftracker.databinding.ActivityGolfRoundListBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfCourseModel
import org.wit.mygolftracker.models.GolfRoundModel
import timber.log.Timber.i

class GolfRoundListActivity : AppCompatActivity(), GolfTrackerListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGolfRoundListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var golfCourses = ArrayList<GolfCourseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGolfRoundListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadGolfRounds()

        registerRefreshCallback()
        registerMapCallback()
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
            R.id.item_map -> {
                val launcherIntent = Intent(this, MapsActivity::class.java)
                //TODO("Try and send locations via putExtra")

                // .putParcelableArrayListExtra("courses", golfCourses)
                mapIntentLauncher.launch(launcherIntent)
            }
            R.id.item_openGallery -> {
            val launcherIntent = Intent(this, ImageGallery::class.java)

            i("Click for image gallery")
            startActivityForResult(launcherIntent,0)
        }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onGolfRoundClick(golfRound: GolfRoundModel) {
        val launcherIntent = Intent(this, GolfRoundActivity::class.java)
        launcherIntent.putExtra("golfRound_edit", golfRound)
        i("Click for update")
        startActivityForResult(launcherIntent,0)
    }

    private fun loadGolfRounds() {
        showGolfRounds(app.golfRounds.findAll())
    }

    fun showGolfRounds (golfRounds: List<GolfRoundModel>) {
        binding.recyclerView.adapter = GolfTrackerAdapter(golfRounds, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadGolfRounds() }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }
}