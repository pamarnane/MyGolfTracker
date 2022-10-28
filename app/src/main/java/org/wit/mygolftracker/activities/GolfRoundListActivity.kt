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

    //var location = GolfCourseModel(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGolfRoundListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

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
                //golfCourses = app.golfRounds.findAllCourses() as MutableList<GolfCourseModel>
                val launcherIntent = Intent(this, MapsActivity::class.java)
                //TODO("Try and send locations via putExtra")

                    .putParcelableArrayListExtra("courses", golfCourses)
                //.putExtra("location", location)
                //startActivityForResult(launcherIntent,0)

                mapIntentLauncher.launch(launcherIntent)
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

/*    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }*/
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }
}