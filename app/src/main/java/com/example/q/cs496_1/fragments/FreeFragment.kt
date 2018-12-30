package com.example.q.cs496_1.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import kotlinx.android.synthetic.main.fragment_free.view.*

class FreeFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_free, container, false)
        view.myWebView.loadUrl("https://play2048.co/")
        return view
    }
}