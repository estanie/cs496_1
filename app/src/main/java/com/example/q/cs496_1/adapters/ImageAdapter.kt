package com.example.q.cs496_1.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.q.cs496_1.models.MyImage
import com.example.q.cs496_1.R
import com.example.q.cs496_1.activities.ImageDetailActivity
import com.example.q.cs496_1.helpers.DialogHelper
import kotlinx.android.synthetic.main.image_entry.view.*
import java.io.File

class ImageAdapter : BaseAdapter {
    private var imageList = ArrayList<MyImage>()
    private var context: Context? = null

    constructor(context: Context) : super() {
        this.context = context
        this.imageList = getAllShownImagesPath(context)
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
        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val imageView = inflator.inflate(R.layout.image_entry, null)
        Glide.with(imageView)
            .load(File(myImage.path!!))
            .into(imageView.myImage)

        imageView.setOnClickListener {
            val intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra("image", imageList[position!!].path)
            context!!.startActivity(intent)
        }

        imageView.setOnLongClickListener {
            DialogHelper().makeYesOrNoDialog(context!!) {
                val file = File(imageList[position!!].path)
                file.delete()
                context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(imageList[position!!].path))))
                imageList.remove(imageList[position!!])
                // TODO(@estanie): Don't refresh, just move!
                notifyDataSetChanged()
            }
            true
        }
        return imageView
    }

    private fun getAllShownImagesPath(context: Context) : ArrayList<MyImage> {
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        val projection: Array<String> = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        var cursor = context.contentResolver.query(uri,projection, null, null, null)
        var allImageList = ArrayList<MyImage>()
        val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            allImageList.add(MyImage(cursor.getString(columnIndexData)))
        }
        return allImageList
    }
}