package com.example.q.cs496_1.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.q.cs496_1.models.MyImage
import com.example.q.cs496_1.R
import com.example.q.cs496_1.activities.ImageDetailActivity
import com.example.q.cs496_1.helpers.DialogHelper
import kotlinx.android.synthetic.main.entry_image.view.*
import java.io.File

class ImageAdapter(val imageList: ArrayList<MyImage>, val context: Context) : RecyclerView.Adapter<ImageAdapter.Holder>() {
    private var mCurrentAnimator: Animator? = null
    private var mShortAnimationDuration: Int = 0

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        val holder = Holder(LayoutInflater.from(context).inflate(R.layout.entry_image, parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        changeHeight(holder)
        holder.bind(context, imageList[position])
    }


    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(context: Context, image: MyImage) {
            mShortAnimationDuration = context.resources.getInteger(android.R.integer.config_shortAnimTime)
            view.imgthumb.setOnClickListener {
                // zoomImageFromThumb(view, image)
                val intent = Intent(context, ImageDetailActivity::class.java)
                intent.putExtra("image", image.path)
                context.startActivity(intent)
            }

            view.imgthumb.setOnLongClickListener {
                DialogHelper().makeYesOrNoDialog(context) {
                    val file = File(image.path)
                    file.delete()
                    context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(image.path))))
                    imageList.remove(image)
                    notifyDataSetChanged()
                }
                true
            }

            Glide.with(context)
                .load(File(image.path))
                .into(view.imgthumb)
        }
    }

    fun addImageToList(path: String) {
        imageList.add(0, MyImage(path))
        notifyDataSetChanged()
    }

    private fun changeHeight(holder: Holder) {
        val displayMetrics = DisplayMetrics()
        val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Log.e("WIDTH", ""+displayMetrics.widthPixels)
        holder.view.imgthumb.layoutParams.width = displayMetrics.widthPixels / 3
        holder.view.imgthumb.layoutParams.height = displayMetrics.widthPixels / 3
    }

    // TODO(@estanie): Don't works T0T
    private fun zoomImageFromThumb(view: View, image: MyImage) {
        mCurrentAnimator?.cancel()

        Glide.with(context)
            .load(File(image.path))
            .into(view.myImage!!)

        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        view.imgthumb.getGlobalVisibleRect(startBoundsInt)
        view.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float =startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        view.imgthumb.alpha = 0f
        view.myImage.visibility = View.VISIBLE
        view.myImage.pivotX = 0f
        view.myImage.pivotY = 0f

        mCurrentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(
                view.myImage,
                View.X,
                startBounds.left,
                finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(view.myImage, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(view.myImage, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(view.myImage, View.SCALE_Y, startScale, 1f))
            }
            duration = mShortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator?) {
                    mCurrentAnimator = null
                }
            })
            start()
        }

        view.myImage.setOnClickListener {
            mCurrentAnimator?.cancel()
            mCurrentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(view.myImage, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(view.myImage, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(view.myImage, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(view.myImage, View.SCALE_Y, startScale))
                }
                duration = mShortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        view.imgthumb.alpha = 1f
                        view.myImage.visibility = View.GONE
                        mCurrentAnimator = null
                    }
                    override fun onAnimationCancel(animation: Animator?) {
                        view.imgthumb.alpha = 1f
                        view.myImage.visibility = View.GONE
                        mCurrentAnimator = null
                    }
                })
                start()
            }
        }
    }
}