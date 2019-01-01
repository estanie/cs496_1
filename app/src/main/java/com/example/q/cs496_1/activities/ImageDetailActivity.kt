package com.example.q.cs496_1.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Window
import com.bumptech.glide.Glide
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageSwipeAdapter
import com.example.q.cs496_1.managers.ImageManager
import kotlinx.android.synthetic.main.activity_image_detail.*
import java.io.File
import java.lang.Exception

class ImageDetailActivity : AppCompatActivity() {
    var position = 0
    // private var gestureDetector: GestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        // gestureDetector = GestureDetector(this@ImageDetailActivity, GestureListener())
        setContentView(R.layout.activity_image_detail)

        val extras= intent.extras
        position = extras.getInt("position")

        var imageSwipeAdapter = ImageSwipeAdapter(supportFragmentManager, position)
        imageSwipeAdapter.addFragment()
        imageViewPager.adapter = imageSwipeAdapter

        // TODO(@estanie): adds Image joom & joom out, Swipe to next, and go back button

    }

    /*
    var x1: Float?= 0.toFloat()
    var x2: Float?= 0.toFloat()
    var velocityX1: Float? = 0.toFloat()
    var velocityX2: Float? = 0.toFloat()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    inner class GestureListener: GestureDetector.SimpleOnGestureListener {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        constructor() {}

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY :Float = e2!!.getY() - e1!!.getY()
                val diffX : Float = e2.getX() - e1.getX()
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) onSwipeLeftToRight()
                        else onSwipeRightToLeft()
                        result = true
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) onSwipeUp()
                    else onSwipeDown()
                    result = true
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return result
        }

        fun onSwipeLeftToRight() {
            if(position > 0) {
                position--
                setImage(position)
            }
        }
        fun onSwipeRightToLeft() {
            if (position+1 < ImageManager.getSize()) {
                position++
                setImage(position)
            }
        }
        fun onSwipeUp() {

        }
        fun onSwipeDown() {

        }
    }
    */
}