package com.example.bvm.ui.video

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import kotlinx.android.synthetic.main.activity_video_info.*


class VideoInfoActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_TITLE = "videoTitle"
        const val VIDEO_INFO = "videoInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)

        val videoTitle = intent.getStringExtra(VIDEO_TITLE) ?: ""
        val videoInfo = intent.getStringExtra(VIDEO_INFO) ?: ""
        setSupportActionBar(videoInfoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        videoInfoCollapsingToolbar.title = videoTitle
//        Glide.with(this).load(url).into(videoInfoImageView)
        videoInfoContextText.text = videoInfo

        videoInfoImageView.setOnClickListener {
            val tmp = videoInfoImageView.drawable as BitmapDrawable
            val bitmap = tmp.bitmap
            bigImageLoader(bitmap)
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

}