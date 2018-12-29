package com.example.q.cs496_1.fragments

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R

import com.example.q.cs496_1.adapters.ImageAdapter
import com.example.q.cs496_1.models.MyImage
import kotlinx.android.synthetic.main.fragment_gallery.view.*

class GalleryFragment: Fragment() {
    private var adapter: ImageAdapter? = null
    private var imageList = ArrayList<MyImage>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        imageList = getAllShownImagesPath(context)
        adapter = ImageAdapter(context, imageList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_gallery, container, false)
        view.imageGrid.adapter = adapter
        view.addImgFab.setOnClickListener { view ->
            // TODO(@estanie): Changes the functions to take picture.
        }
        return view
    }

    private fun getAllShownImagesPath(context: Context) : ArrayList<MyImage> {
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        val projection: Array<String> = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        var cursor = context.contentResolver.query(uri,projection, null, null, null)
        var allImageList = ArrayList<MyImage>()
        var imagePath: String
        val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            allImageList.add(MyImage(cursor.getString(columnIndexData)))
        }
        return allImageList
    }
}