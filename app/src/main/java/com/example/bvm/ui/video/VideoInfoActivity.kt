package com.example.bvm.ui.video

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bvm.BVMApplication
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import kotlinx.android.synthetic.main.activity_video_info.*


class VideoInfoActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_TITLE = "videoTitle"
        const val VIDEO_INFO = "videoInfo"
        const val VIDEO_IMAGE = "videoImage"
        const val VIDEO_ACTOR = "videoActor"
        const val VIDEO_ACTOR_INFO = "videoActorInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)

        val videoTitle = intent.getStringExtra(VIDEO_TITLE) ?: ""
        val videoInfo = intent.getStringExtra(VIDEO_INFO) ?: ""
        val videoPic = intent.getStringExtra(VIDEO_IMAGE) ?: ""
        val videoActor = intent.getStringExtra(VIDEO_ACTOR) ?: ""
        val videoActorInfo = intent.getStringExtra(VIDEO_ACTOR_INFO) ?: ""
        setSupportActionBar(videoInfoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        videoInfoCollapsingToolbar.title = videoTitle
        Glide.with(this).load(videoPic).into(videoInfoImageView)
        videoInfoContextText.text = videoInfo
        videoActorContextText.text = videoActor
        videoActorInfoContextText.text = videoActorInfo

        videoInfoImageView.setOnClickListener {
//            val tmp = videoInfoImageView.drawable as BitmapDrawable
//            val bitmap = tmp.bitmap
//            bigImageLoader(bitmap)
            load(videoPic) { bitmap ->
                bigImageLoader(bitmap)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bigImageLoader(bitmap: Bitmap) {
        val dialog = Dialog(this)
        val image = ImageView(context)
        image.setImageBitmap(bitmap)
        dialog.setContentView(image)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        image.setOnClickListener { dialog.cancel() }
    }

    /**
     * 加载网络地址 [url] 图片返回 Bitmap
     */
    private fun load(url: String, success: (Bitmap) -> Unit) {
        Glide.with(BVMApplication.context) // context，可添加到参数中
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // 成功返回 Bitmap
                    success.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }


}