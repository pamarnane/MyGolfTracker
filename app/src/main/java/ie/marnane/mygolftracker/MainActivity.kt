package ie.marnane.mygolftracker

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import ie.marnane.mygolftracker.databinding.ActivityMainBinding
import ie.marnane.mygolftracker.models.GolfCourseModel
import ie.marnane.mygolftracker.models.GolfTrackerManager

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.roundListFragment, R.id.nav_gallery, R.id.roundFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val golfCourse = GolfCourseModel()

        golfCourse.title = "Ennis GC"
        golfCourse.lat = 52.84092016654442
        golfCourse.lng = -8.998135028815737
        golfCourse.icon = "ennis"
        GolfTrackerManager.createCourse(golfCourse.copy())


        golfCourse.title = "Dromoland Castle GC"
        golfCourse.lat = 52.78218837292427
        golfCourse.lng = -8.909143400919719
        golfCourse.icon = "dromoland"
        GolfTrackerManager.createCourse(golfCourse.copy())

        golfCourse.title = "Lahinch GC - Old"
        golfCourse.lat = 52.93535192772572
        golfCourse.lng = -9.345525869316168
        golfCourse.icon = "lahinch_old"
        GolfTrackerManager.createCourse(golfCourse.copy())

        golfCourse.title = "Lahinch GC - Castle"
        golfCourse.lat = 52.93535192772572
        golfCourse.lng = -9.345525869316168
        golfCourse.icon = "lahinch_castle"
        GolfTrackerManager.createCourse(golfCourse.copy())

        golfCourse.title = "Shannon GC"
        golfCourse.lat = 52.68938272732169
        golfCourse.lng = -8.937344286642613
        golfCourse.icon = "lahinch"
        GolfTrackerManager.createCourse(golfCourse.copy())

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}