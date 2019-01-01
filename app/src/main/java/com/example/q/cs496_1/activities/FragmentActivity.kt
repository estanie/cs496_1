package com.example.q.cs496_1.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.MyPagerAdapter
import kotlinx.android.synthetic.main.framgent_main.*

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.framgent_main)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabsMain.setupWithViewPager(viewPager)

    }
}