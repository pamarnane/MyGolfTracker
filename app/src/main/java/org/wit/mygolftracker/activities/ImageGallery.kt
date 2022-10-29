package org.wit.mygolftracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import org.wit.mygolftracker.R
import org.wit.mygolftracker.adapters.ImageGalleryAdapter
import org.wit.mygolftracker.adapters.ImageGalleryListener
import org.wit.mygolftracker.databinding.ActivityImageGalleryBinding
import org.wit.mygolftracker.main.MainApp
import org.wit.mygolftracker.models.GolfRoundModel
import timber.log.Timber.i

class ImageGallery : AppCompatActivity(), ImageGalleryListener {
    lateinit var app : MainApp
    lateinit var binding: ActivityImageGalleryBinding
    lateinit var searchView: SearchView
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        searchView = binding.imageSearch
        val layoutManager = GridLayoutManager(this, 3)
        binding.recyclerImageView.layoutManager = layoutManager
        loadImages()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               // i("$query")
                filterImages(query)
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
               // i("$msg")
                filterImages(msg)
                return false
            }
        })

        registerRefreshCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerImageView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadImages() }
    }

    private fun loadImages() {
        showImages(app.golfRounds.findAllwImages())
    }

    fun showImages (golfRounds: List<GolfRoundModel>) {
        binding.recyclerImageView.adapter = ImageGalleryAdapter(golfRounds, this)
        binding.recyclerImageView.adapter?.notifyDataSetChanged()
    }

    fun filterImages (query: String?) {
        val golfRounds = app.golfRounds.findAllwImages()

        val filteredGolfRounds = golfRounds.filter { it.course.lowercase().contains("$query".lowercase()) }
        binding.recyclerImageView.adapter = ImageGalleryAdapter(filteredGolfRounds, this)
        binding.recyclerImageView.adapter?.notifyDataSetChanged()
    }

    override fun onImageClick(golfRound: GolfRoundModel) {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_imagegallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}