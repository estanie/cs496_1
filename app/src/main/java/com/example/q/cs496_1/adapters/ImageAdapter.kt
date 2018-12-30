package com.example.q.cs496_1.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.q.cs496_1.models.MyImage
import com.example.q.cs496_1.R
import com.example.q.cs496_1.activities.ImageDetailActivity
import com.example.q.cs496_1.helpers.DialogHelper
import java.io.File

class ImageAdapter(val imageList: ArrayList<MyImage>, val context: Context) : RecyclerView.Adapter<ImageAdapter.Holder>() {

    override fun getItemCount(): Int {
        Log.e("IMAGE SIZE", ""+imageList.size)
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        val holder = Holder(LayoutInflater.from(context).inflate(R.layout.image_entry, parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        changeHeight(holder)
        holder.bind(context, imageList[position])
    }


    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.myImage)

        fun bind(context: Context, image: MyImage) {
            Log.e("BIND", "IMAGE BIND")
            Glide.with(context)
                .load(File(image.path))
                .into(imageView!!)

            imageView.setOnClickListener {
                val intent = Intent(context, ImageDetailActivity::class.java)
                intent.putExtra("image", image.path)
                context.startActivity(intent)
            }

            imageView.setOnLongClickListener {
                DialogHelper().makeYesOrNoDialog(context) {
                    val file = File(image.path)
                    file.delete()
                    context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(image.path))))
                    imageList.remove(image)
                    notifyDataSetChanged()
                }
                true
            }
        }
    }

    fun addImageToList(path: String) {
        imageList.add(MyImage(path))
        notifyDataSetChanged()
    }

    private fun changeHeight(holder: Holder) {
        val displayMetrics = DisplayMetrics()
        val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Log.e("WIDTH", ""+displayMetrics.widthPixels)
        holder.imageView.layoutParams.width = displayMetrics.widthPixels / 3
        holder.imageView.layoutParams.height = displayMetrics.widthPixels / 3
    }
}