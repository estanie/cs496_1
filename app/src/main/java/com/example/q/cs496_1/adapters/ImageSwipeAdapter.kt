package com.example.q.cs496_1.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.q.cs496_1.fragments.ImageFragment
import com.example.q.cs496_1.managers.ImageManager

class ImageSwipeAdapter(fm: FragmentManager, position: Int): FragmentPagerAdapter(fm) {
    var position = position
    private var fragmentList = ArrayList<Fragment>()

    override fun getItem(position:Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment() {
        // TODO(@estanie): 킬때마다 계속 새로 만든다...
        for (i in 0..ImageManager.getSize()-1) {
            fragmentList.add(ImageFragment().newInstance(i))
        }
    }

}