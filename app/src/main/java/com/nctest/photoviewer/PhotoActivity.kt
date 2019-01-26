package com.nctest.photoviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.nctest.photoviewer.model.Photo
import com.nctest.photoviewer.utils.Constants
import com.squareup.picasso.Picasso

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        if (intent.hasExtra(Constants.PHOTO_EXTRA)) {
            val photo: Photo = intent.getParcelableExtra(Constants.PHOTO_EXTRA)
            Picasso.get()
                    .load(photo.url)
                    .into(findViewById<ImageView>(R.id.image))
        }

    }
}
