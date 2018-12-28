package com.example.q.cs496_1.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R


import com.example.q.cs496_1.adapters.ImageAdapter
import com.example.q.cs496_1.models.MyImage
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment: Fragment(){
    private val REQUEST_CODE_BROWSE_IMAGE: Int = 1
    var adapter: ImageAdapter? = null
    var imageList = ArrayList<MyImage>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ImageAdapter(context, imageList)
    }

    override fun onStart() {
        super.onStart()
        imageGrid.adapter = adapter

        // TODO(@estanie): Saves the data using json.
        addImgFab.setOnClickListener {view ->
            val intent= Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, REQUEST_CODE_BROWSE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode:Int, data: Intent) {
        if (requestCode == REQUEST_CODE_BROWSE_IMAGE) {
            if (resultCode == RESULT_OK) {
                val fileUri :Uri = data.data
                imageList.add(MyImage(fileUri))
            }
        }
    }
}