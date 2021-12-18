package com.example.bvm.ui.video

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bvm.BVMApplication
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.activity_book_info.*
import kotlinx.android.synthetic.main.activity_music_info.*
import kotlinx.android.synthetic.main.activity_video_info.*
import kotlin.concurrent.thread


class VideoInfoActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }

    companion object {
        const val VIDEO_ID = "videoId"
        const val VIDEO_TITLE = "videoTitle"
        const val VIDEO_INFO = "videoInfo"
        const val VIDEO_IMAGE = "videoImage"
        const val VIDEO_ACTOR = "videoActor"
        const val VIDEO_ACTOR_INFO = "videoActorInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)

        val videoId = intent.getStringExtra(VIDEO_ID) ?: ""
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

        videoInfoCommit.setOnClickListener {
            val intent = Intent(context, VideoCommentActivity::class.java).apply {
                putExtra(VIDEO_ID, videoId)
            }
            startActivity(intent)
        }

        viewModel.commentLiveData.observe(this, Observer { result ->
            val comments = result.getOrNull()
            if (comments != null) {
                viewModel.commentList.clear()
                viewModel.commentList.addAll(comments)
            } else {
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.searchVideoCommentByVideoId(videoId)

        thread {
            Thread.sleep(100)
            var totalRating = 0F
            for (i in 0 until viewModel.commentList.size) {
                totalRating += viewModel.commentList[i].rating
            }
            totalRating /= viewModel.commentList.size
            videoTotalRatingBar.rating = totalRating

//            Toast.makeText(context, "${viewModel.commentList.size}", Toast.LENGTH_SHORT).show()
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