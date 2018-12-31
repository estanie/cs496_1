package com.example.q.cs496_1.fragments

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.ImageAdapter
import com.example.q.cs496_1.models.MyImage
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
    var imageList : ArrayList<MyImage>? = null

    override fun onAttach(cotext: Context) {
        super.onAttach(context)
        imageList = getAllShownImagesPath(context!!)
        adapter = ImageAdapter(imageList!!, context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.fragment_gallery, container, false)
        view.imageGrid.adapter = adapter
        view.imageGrid.layoutManager = GridLayoutManager(context, 3)

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

    private fun getAllShownImagesPath(context: Context) : ArrayList<MyImage> {
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
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