package com.example.bvm.ui.video

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.logic.model.VideoComment
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.activity_video_comment.*
import java.util.*

class VideoCommentActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }
    var rating: Float = 2.5F

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_comment)
        setSupportActionBar(videoCommentToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
        val videoId = intent.getStringExtra(VideoInfoActivity.VIDEO_ID) ?: "0"

        videoRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
//            Toast.makeText(this, "rating: $rating", Toast.LENGTH_SHORT).show()
            this.rating = rating
        }

        videoRatingCommentFab.setOnClickListener {
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val comment = videoCommentText.editText?.text.toString()
            viewModel.insertVideoComment(VideoComment(videoId.toLong(), BVMApplication.USER?.user_id, comment, rating, dateFormat.format(date)))
            finish()
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
}