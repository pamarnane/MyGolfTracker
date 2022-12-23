package ie.marnane.mygolftracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import ie.marnane.mygolftracker.databinding.ActivityMainBinding
import ie.marnane.mygolftracker.databinding.NavHeaderMainBinding
import ie.marnane.mygolftracker.firebase.FirebaseDBManager
import ie.marnane.mygolftracker.firebase.FirebaseImageManager
import ie.marnane.mygolftracker.models.GolfCourseModel
import ie.marnane.mygolftracker.models.GolfTrackerManager
import ie.marnane.mygolftracker.ui.auth.LoggedInViewModel
import ie.marnane.mygolftracker.ui.auth.Login

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var loggedInViewModel : LoggedInViewModel
    private lateinit var navHeaderMainBinding : NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

/*        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_MyGolfTrackerDark) //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.Theme_MyGolfTracker)  //default app theme
        }*/
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
            R.id.roundListFragment, R.id.nav_gallery, R.id.roundFragment, R.id.mapFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val golfCourse = GolfCourseModel()

        golfCourse.title = "Ennis GC"
        golfCourse.lat = 52.84092016654442
        golfCourse.lng = -8.998135028815737
        golfCourse.icon = "ennis"
        GolfTrackerManager.createCourse(golfCourse.copy())
//        FirebaseDBManager.createCourse(loggedInViewModel.liveFirebaseUser, golfCourse)


        golfCourse.title = "Dromoland Castle GC"
        golfCourse.lat = 52.78218837292427
        golfCourse.lng = -8.909143400919719
        golfCourse.icon = "dromoland"
        GolfTrackerManager.createCourse(golfCourse.copy())
//        FirebaseDBManager.createCourse(loggedInViewModel.liveFirebaseUser, golfCourse)

        golfCourse.title = "Lahinch GC - Old"
        golfCourse.lat = 52.93535192772572
        golfCourse.lng = -9.345525869316168
        golfCourse.icon = "lahinch_old"
        GolfTrackerManager.createCourse(golfCourse.copy())
//        FirebaseDBManager.createCourse(loggedInViewModel.liveFirebaseUser, golfCourse)

        golfCourse.title = "Lahinch GC - Castle"
        golfCourse.lat = 52.93535192772572
        golfCourse.lng = -9.345525869316168
        golfCourse.icon = "lahinch_castle"
        GolfTrackerManager.createCourse(golfCourse.copy())
//        FirebaseDBManager.createCourse(loggedInViewModel.liveFirebaseUser, golfCourse)

        golfCourse.title = "Shannon GC"
        golfCourse.lat = 52.68938272732169
        golfCourse.lng = -8.937344286642613
        golfCourse.icon = "lahinch"
        GolfTrackerManager.createCourse(golfCourse.copy())
//        FirebaseDBManager.createCourse(loggedInViewModel.liveFirebaseUser, golfCourse)

    }

    public override fun onStart() {
        super.onStart()
        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel.liveFirebaseUser.observe(this, Observer { firebaseUser ->
            if (firebaseUser != null) {
                val currentUser = loggedInViewModel.liveFirebaseUser.value
                if (currentUser != null) updateNavHeader(loggedInViewModel.liveFirebaseUser.value!!)

            }
        })

        loggedInViewModel.loggedOut.observe(this, Observer { loggedout ->
            if (loggedout) {
                startActivity(Intent(this, Login::class.java))
            }
        })


    }

    private fun updateNavHeader(currentUser: FirebaseUser) {
        var headerView = binding.navView.getHeaderView(0)
        navHeaderMainBinding = NavHeaderMainBinding.bind(headerView)
        navHeaderMainBinding.navHeaderEmail.text = currentUser.email
        if (currentUser.photoUrl != null) {
            //if you're a google user
            FirebaseImageManager.updateUserImage(
                currentUser.uid,
                currentUser.photoUrl,
                navHeaderMainBinding.imageView,
                false
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut()
        //Launch Login activity and clear the back stack to stop navigating back to the Home activity
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun changeTheme(item: MenuItem) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    class MyPreferences(context: Context?) {

        companion object {
            private const val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"
        }

        private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        var darkMode = preferences.getInt(DARK_STATUS, 0)
            set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()

    }
}