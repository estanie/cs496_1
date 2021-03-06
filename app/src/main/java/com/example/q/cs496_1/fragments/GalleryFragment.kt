package com.example.q.cs496_1.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageAdapter
import com.example.q.cs496_1.managers.ImageManager
import com.example.q.cs496_1.models.MyImage
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class GalleryFragment: Fragment() {
    private var adapter: ImageAdapter? = null
    private var IMAGE_CAPTURED = false
    val REQUEST_IMAGE_CAPTURE = 1
    var mCurrentPhotoPath = ""
    val IMAGE_PATH = "/storage/emulated/0/DCIM/cs496_1"
    var imageList : ArrayList<MyImage>? = null

    override fun onAttach(cotext: Context) {
        super.onAttach(context)
        ImageManager.getAllShownImagesPath(context!!)
        adapter = ImageAdapter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.fragment_gallery, container, false)
        view.imageGrid.adapter = adapter
        view.imageGrid.layoutManager = GridLayoutManager(context, 3) as RecyclerView.LayoutManager?

        view.addImgFab.setOnClickListener { view ->
            dispatchTakePictureIntent()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        galleryAddPic()
        Log.e("On START", "START!")
    }

    // TODO(@estanie): It would be seperate to camera utils, Also do not use this way T-T. :0
    private fun dispatchTakePictureIntent() {
        IMAGE_CAPTURED = true
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(context!!, context!!.packageName+".fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = File(IMAGE_PATH)
        if (!storageDir.exists()) storageDir.mkdir()
        return File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir).apply {
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        if (!IMAGE_CAPTURED) return
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {mediaScanIntent ->
            val f = File(mCurrentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            Log.e("mCurrentPhotoPath", mediaScanIntent.data.path+"")
            val checkFile = File(mediaScanIntent.data.path)
            context!!.sendBroadcast(mediaScanIntent)
            if (!checkFile.exists()) return
            adapter!!.addImageToList(mCurrentPhotoPath)
            IMAGE_CAPTURED = false
            // TODO(@estanie): If take picture canceled, remove this.
        }
    }
}