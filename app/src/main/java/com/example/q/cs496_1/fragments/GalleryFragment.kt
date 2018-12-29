package com.example.q.cs496_1.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageAdapter
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class GalleryFragment: Fragment() {
    private var adapter: ImageAdapter? = null
    val REQUEST_IMAGE_CAPTURE = 1
    var mCurrentPhotoPath = ""
    val IMAGE_PATH = "/storage/emulated/0/DCIM/cs496_1"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ImageAdapter(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_gallery, container, false)
        view.imageGrid.adapter = adapter
        view.addImgFab.setOnClickListener { view ->
            dispatchTakePictureIntent()
        }
        return view
    }

    // TODO(@estanie): It would be seperate to camera utils. :0
    private fun dispatchTakePictureIntent() {
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
                    galleryAddPic()
                }

            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = File(IMAGE_PATH)
        Log.e("storigeDir", storageDir.path)
        if (!storageDir.exists()) storageDir.mkdir()
        return File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir).apply {
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {mediaScanIntent ->
            val f = File(mCurrentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            context!!.sendBroadcast(mediaScanIntent)
            adapter!!.addImageToList(mCurrentPhotoPath)
        }
    }
}