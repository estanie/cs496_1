package com.example.q.cs496_1.managers

import android.support.v4.app.Fragment
import com.example.q.cs496_1.fragments.ImageFragment

object FragmentListManager {
    private var fragmentList = ArrayList<Fragment>()

    fun getSize() : Int {
        return fragmentList.size
    }
    fun getFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    fun remove(image: Fragment) {
        fragmentList.remove(image)
    }

    fun makeFragmentList() {
        for (i in 0..ImageManager.getSize()-1) {
            add(ImageFragment().newInstance(i))
        }
    }
    fun add(fragment: Fragment) {
        fragmentList.add(fragment)
    }
    fun add(position: Int) {
        fragmentList.add(position, ImageFragment().newInstance(position))
    }


}