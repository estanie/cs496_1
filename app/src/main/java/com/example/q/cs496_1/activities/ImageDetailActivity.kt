package com.example.q.cs496_1.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.q.cs496_1.R
import kotlinx.android.synthetic.main.activity_image_detail.*
import kotlinx.android.synthetic.main.image_entry.view.*
import java.io.File

class ImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val extras= intent.extras

        // TODO(@estanie): adds Image joom & joom out, Swipe to next, and go back button
        Glide.with(imgDetail)
            .load(File(extras.getString("image")))
            .into(imgDetail)

    }

}