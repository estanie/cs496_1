package com.example.q.cs496_1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.q.cs496_1.models.MyImage
import com.example.q.cs496_1.R
import kotlinx.android.synthetic.main.image_entry.view.*

class ImageAdapter : BaseAdapter {
    var imageList = ArrayList<MyImage>()
    var context: Context? = null

    constructor(context: Context, imageList: ArrayList<MyImage>) : super() {
        this.context = context
        this.imageList = imageList
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItem(position: Int): Any {
        return imageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val myImage = this.imageList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var imageView = inflator.inflate(R.layout.image_entry, null)
        imageView.myImage.setImageResource(myImage.image!!)

        return imageView
    }
}