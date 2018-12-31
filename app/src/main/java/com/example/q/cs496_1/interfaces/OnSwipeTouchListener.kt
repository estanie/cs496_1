package com.example.q.cs496_1.interfaces

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.Exception

public class OnSwipeTouchListener : View.OnTouchListener {
    private var gestureDetector: GestureDetector? = null
    constructor(context: Context) {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }
    private class GestureListener: GestureDetector.SimpleOnGestureListener {
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
                        if (diffX > 0) onSwipeRight()
                        else onSwipeLeft()
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

       fun onSwipeLeft() {

        }
        fun onSwipeRight() {

        }
        fun onSwipeUp() {

        }
        fun onSwipeDown() {

        }
    }

}