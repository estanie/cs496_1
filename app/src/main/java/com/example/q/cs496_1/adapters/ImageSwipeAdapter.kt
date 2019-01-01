package com.example.q.cs496_1.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.q.cs496_1.fragments.ImageFragment
import com.example.q.cs496_1.managers.FragmentListManager
import com.example.q.cs496_1.managers.ImageManager

class ImageSwipeAdapter(fm: FragmentManager, position: Int): FragmentPagerAdapter(fm) {
    var position = position

    override fun getItem(position:Int): Fragment {
        return FragmentListManager.getFragment(position)
    }

    override fun getCount(): Int {
        return FragmentListManager.getSize()
    }
}