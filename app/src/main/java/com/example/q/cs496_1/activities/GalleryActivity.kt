package com.example.q.cs496_1.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.q.cs496_1.models.MyImage
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageAdapter
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {
    var adapter: ImageAdapter? = null
    var imageList = ArrayList<MyImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        // @TODO(gayeon): Adds default images and adds functions that get image from local.

        adapter = ImageAdapter(this, imageList)
        imageGrid.adapter = adapter
    }
}