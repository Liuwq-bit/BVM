package com.example.bvm.ui.music

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bvm.BVMApplication
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.ui.book.BookInfoActivity
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import kotlinx.android.synthetic.main.activity_book_info.*
import kotlinx.android.synthetic.main.activity_music_info.*
import kotlinx.android.synthetic.main.activity_video_info.*
import kotlinx.android.synthetic.main.activity_video_info.videoInfoToolbar
import kotlin.concurrent.thread

class MusicInfoActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }

    companion object {
        const val MUSIC_ID = "musicId"
        const val MUSIC_TITLE = "musicTitle"
        const val MUSIC_LABEL = "musicLabel"
        const val MUSIC_PUBLISH_TIME = "musicPublishTime"
        const val MUSIC_INFO = "musicInfo"
        const val MUSIC_IMAGE = "musicImage"
        const val MUSIC_SINGER = "musicSinger"
        const val MUSIC_SINGER_INFO = "musicSingerInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_info)

        val musicId = intent.getStringExtra(MUSIC_ID) ?: ""
        val musicTitle = intent.getStringExtra(MUSIC_TITLE) ?: ""
        val musicInfo = intent.getStringExtra(MUSIC_INFO) ?: ""
        val musicPic = intent.getStringExtra(MUSIC_IMAGE) ?: ""
        val musicSinger = intent.getStringExtra(MUSIC_SINGER) ?: ""
        val musicSingerInfo = intent.getStringExtra(MUSIC_SINGER_INFO) ?: ""
        val musicLabel = intent.getStringExtra(MusicInfoActivity.MUSIC_LABEL) ?: ""
        val musicPublishTime = intent.getStringExtra(MusicInfoActivity.MUSIC_PUBLISH_TIME) ?: ""
        setSupportActionBar(musicInfoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        musicInfoCollapsingToolbar.title = musicTitle
        Glide.with(this).load(musicPic).into(musicInfoImageView)
        musicInfoContextText.text = musicInfo
        musicSingerContextText.text = musicSinger
        musicSingerInfoContextText.text = musicSingerInfo

        musicInfoImageView.setOnClickListener {
//            val tmp = musicInfoImageView.drawable as BitmapDrawable
//            val bitmap = tmp.bitmap
//            bigImageLoader(bitmap)
            load(musicPic) { bitmap ->
                bigImageLoader(bitmap)
            }
        }

        musicInfoCommit.setOnClickListener {
            val intent = Intent(context, MusicCommentActivity::class.java).apply {
                putExtra(MUSIC_ID, musicId)
            }
            startActivity(intent)
        }

        musicInfoChangeFab.setOnClickListener {
            val intent = Intent(context, MusicChangeActivity::class.java).apply {
                putExtra(MusicInfoActivity.MUSIC_TITLE, musicTitle)
                putExtra(MusicInfoActivity.MUSIC_INFO, musicInfo)
                putExtra(MusicInfoActivity.MUSIC_IMAGE, musicPic)
                putExtra(MusicInfoActivity.MUSIC_SINGER, musicSinger)
                putExtra(MusicInfoActivity.MUSIC_SINGER_INFO, musicSingerInfo)
                putExtra(MusicInfoActivity.MUSIC_ID, musicId)
                putExtra(MusicInfoActivity.MUSIC_LABEL, musicLabel)
                putExtra(MusicInfoActivity.MUSIC_PUBLISH_TIME, musicPublishTime)
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

        viewModel.searchMusicCommentByMusicId(musicId)

        thread {
            Thread.sleep(100)
            var totalRating = 0F
            for (i in 0 until viewModel.commentList.size) {
                totalRating += viewModel.commentList[i].rating
            }
            totalRating /= viewModel.commentList.size
            musicTotalRatingBar.rating = totalRating

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
        val image = ImageView(BVMApplication.context)
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