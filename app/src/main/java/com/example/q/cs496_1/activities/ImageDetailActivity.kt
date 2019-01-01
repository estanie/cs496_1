package com.example.q.cs496_1.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Window
import com.bumptech.glide.Glide
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageSwipeAdapter
import com.example.q.cs496_1.managers.FragmentListManager
import com.example.q.cs496_1.managers.ImageManager
import kotlinx.android.synthetic.main.activity_image_detail.*
import java.io.File
import java.lang.Exception

class ImageDetailActivity : AppCompatActivity() {
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_image_detail)

        val extras= intent.extras
        position = extras.getInt("position")

        var imageSwipeAdapter = ImageSwipeAdapter(supportFragmentManager, position)
        if (FragmentListManager.getSize() == 0) FragmentListManager.makeFragmentList()
        imageViewPager.adapter = imageSwipeAdapter
        imageViewPager.currentItem = position
    }
}