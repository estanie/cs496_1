package com.example.q.cs496_1.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R


import com.example.q.cs496_1.adapters.ImageAdapter
import com.example.q.cs496_1.models.MyImage
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment: Fragment(){
    var adapter: ImageAdapter? = null
    var imageList = ArrayList<MyImage>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       // @TODO(gayeon): Adds default images and adds functions that get image from local
        imageList.add(MyImage(R.drawable.abc_btn_radio_to_on_mtrl_000))
        imageList.add(MyImage(R.drawable.abc_btn_radio_to_on_mtrl_000))
        imageList.add(MyImage(R.drawable.abc_btn_radio_to_on_mtrl_000))
        imageList.add(MyImage(R.drawable.abc_btn_radio_to_on_mtrl_000))
        imageList.add(MyImage(R.drawable.abc_btn_radio_to_on_mtrl_000))
        imageList.add(MyImage(R.drawable.abc_btn_radio_to_on_mtrl_000))

        return inflater!!.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ImageAdapter(context, imageList)
    }

    override fun onStart() {
        super.onStart()
        imageGrid.adapter = adapter
    }
}