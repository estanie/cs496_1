package com.example.q.cs496_1.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.q.cs496_1.fragments.AddressFragment
import com.example.q.cs496_1.fragments.FreeFragment
import com.example.q.cs496_1.fragments.GalleryFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                AddressFragment()
            }
            1 -> {
                GalleryFragment()
            }
            else -> {
                return FreeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "PhoneBook"
            1 -> "Gallery"
            else -> return "Free"
        }
    }
}