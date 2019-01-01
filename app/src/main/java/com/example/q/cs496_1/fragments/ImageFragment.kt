package com.example.q.cs496_1.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.q.cs496_1.R
import com.example.q.cs496_1.managers.ImageManager
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.entry_image.view.*
import kotlinx.android.synthetic.main.fragment_image.view.*
import java.io.File
import android.R.attr.y
import android.R.attr.x
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.io.ByteArrayOutputStream


class ImageFragment: Fragment() {

    fun newInstance(pos: Int): Fragment {
        val args = Bundle()
        args.putInt("position", pos)
        val fragment = ImageFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val pos = arguments!!.getInt("position")
        val touchImageView = TouchImageView(context)

        loadImage(view.touchImage, ImageManager.getImage(pos).path!!)

        return view
    }

    private fun loadImage(view: TouchImageView, path:String) {
        val myBitmap : Bitmap = BitmapFactory.decodeFile(path)
        val simpleTarget = object: SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                view.setImageBitmap(myBitmap)
            }
        }
        Glide.with(this)
            .asBitmap()
            .load(File(path))
            .into(simpleTarget)
    }
}