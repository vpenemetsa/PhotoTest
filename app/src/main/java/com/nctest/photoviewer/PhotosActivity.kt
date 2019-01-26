package com.nctest.photoviewer

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.widget.GridView
import com.nctest.photoviewer.adapter.PhotoAdapter
import com.nctest.photoviewer.model.Photo
import com.nctest.photoviewer.network.NetworkManager
import com.nctest.photoviewer.utils.Constants

class PhotosActivity : AppCompatActivity() {

    companion object {
        const val PHOTOS_KEY = "photos"
    }

    private lateinit var adapter: PhotoAdapter
    private val networkManager: NetworkManager = NetworkManager()

    private lateinit var grid: GridView
    private lateinit var refresh: SwipeRefreshLayout

    private var shouldSaveInstance = true

    private val callback: NetworkManager.Callback = object : NetworkManager.Callback {
        override fun onSuccess(photos: ArrayList<Photo>, cached: Boolean) {
            refresh.isRefreshing = false
            adapter.setData(photos)
        }

        override fun onError(networkError: Boolean) {
            refresh.isRefreshing = false
            // Show no network error
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupInteractions()
        adapter = PhotoAdapter(applicationContext)
        grid.adapter = adapter

        if (savedInstanceState == null || !savedInstanceState.containsKey(PHOTOS_KEY)) {
            refresh.isRefreshing = true
            networkManager.getData(callback, false)
        } else {
            adapter.setData(savedInstanceState.getParcelableArrayList(PHOTOS_KEY))
        }
    }

    override fun onResume() {
        super.onResume()
        shouldSaveInstance = true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (!adapter.isEmpty && shouldSaveInstance) {
            outState?.putParcelableArrayList(PHOTOS_KEY, adapter.data)
        }
    }

    private fun setupViews() {
        grid = findViewById(R.id.grid_view)
        refresh = findViewById(R.id.refresh)
    }

    private fun setupInteractions() {
        refresh.setOnRefreshListener {
            refresh.isRefreshing = true
            networkManager.getData(callback, true)
        }

        grid.setOnItemClickListener { _, _, position, _ ->
            shouldSaveInstance = false
            val photo: Photo = adapter.getItem(position)
            val intent = Intent(this, PhotoActivity::class.java)
            intent.putExtra(Constants.PHOTO_EXTRA, photo)
            startActivity(intent)
        }
    }
}
