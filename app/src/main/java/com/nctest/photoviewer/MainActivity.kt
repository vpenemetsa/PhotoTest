package com.nctest.photoviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.GridView
import com.nctest.photoviewer.adapter.PhotoAdapter
import com.nctest.photoviewer.model.Photo
import com.nctest.photoviewer.network.NetworkManager

class MainActivity : AppCompatActivity() {

    companion object {
        const val PHOTOS_KEY = "photos"
    }

    private lateinit var adapter: PhotoAdapter
    private val networkManager: NetworkManager = NetworkManager()

    private lateinit var grid: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        grid = findViewById(R.id.grid_view)

        if (savedInstanceState == null || !savedInstanceState.containsKey(PHOTOS_KEY)) {
            networkManager.getData(object : NetworkManager.Callback {
                override fun onSuccess(photos: ArrayList<Photo>, cached: Boolean) {
                    adapter = PhotoAdapter(applicationContext, photos)
                    grid.adapter = adapter
                }

                override fun onError(networkError: Boolean) {
                    // Show no network error
                }
            }, false)
        } else {
            adapter = PhotoAdapter(applicationContext, savedInstanceState.getParcelableArrayList(PHOTOS_KEY))
            grid.adapter = adapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (!adapter.isEmpty) {
            outState?.putParcelableArrayList(PHOTOS_KEY, adapter.data)
        }
    }
}
