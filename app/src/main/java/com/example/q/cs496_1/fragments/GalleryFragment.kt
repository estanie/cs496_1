package com.example.q.cs496_1.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageAdapter
import kotlinx.android.synthetic.main.fragment_gallery.view.*

class GalleryFragment: Fragment() {
    private var adapter: ImageAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ImageAdapter(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_gallery, container, false)
        view.imageGrid.adapter = adapter
        view.addImgFab.setOnClickListener { view ->
            // TODO(@estanie): Changes the functions to take picture.
        }
        return view
    }
}