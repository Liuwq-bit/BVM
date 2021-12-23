package com.example.bvm.ui.video

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.logic.model.Actor
import com.example.bvm.logic.model.Video
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.activity_video_change.*
import kotlinx.android.synthetic.main.video_add.*
import java.text.SimpleDateFormat
import java.util.*

class VideoChangeActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_change)
        setSupportActionBar(changeVideoToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }


        val videoId = intent.getStringExtra(VideoInfoActivity.VIDEO_ID) ?: ""
        val videoTitle = intent.getStringExtra(VideoInfoActivity.VIDEO_TITLE) ?: ""
        val videoInfo = intent.getStringExtra(VideoInfoActivity.VIDEO_INFO) ?: ""
        val videoPic = intent.getStringExtra(VideoInfoActivity.VIDEO_IMAGE) ?: ""
        val videoAuthor = intent.getStringExtra(VideoInfoActivity.VIDEO_ACTOR) ?: ""
        val videoAuthorInfo = intent.getStringExtra(VideoInfoActivity.VIDEO_ACTOR_INFO) ?: ""
        val videoLabel = intent.getStringExtra(VideoInfoActivity.VIDEO_LABEL) ?: ""
        val videoPublishTime = intent.getStringExtra(VideoInfoActivity.VIDEO_PUBLISH_TIME) ?: ""

        changeVideoNameText.editText?.setText(videoTitle)
        changeVideoActorText.editText?.setText(videoAuthor)
        changeVideoActorInfoText.editText?.setText(videoAuthorInfo)
        changeVideoLabelText.editText?.setText(videoLabel)
        changeVideoInfoText.editText?.setText(videoInfo)
        changeVideoPublishTimeText.editText?.setText(videoPublishTime)
        changeVideoPicText.editText?.setText(videoPic)

        changeVideoCommitFab.setOnClickListener {
            // todo 增加格式判断机制
            val videoName = changeVideoNameText.editText?.text.toString()
//            val videoType = videoTypeText.editText?.text.toString()
            val videoType = "0"
            val videoActor = changeVideoActorText.editText?.text.toString()
            val videoActorInfo = changeVideoActorInfoText.editText?.text.toString()
            val videoLabel = changeVideoLabelText.editText?.text.toString()
            val videoInfo = changeVideoInfoText.editText?.text.toString()
            val videoPublish = changeVideoPublishTimeText.editText?.text.toString()
            val videoPic = changeVideoPicText.editText?.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            val video = Video(
                videoId.toLong(),
                videoName,
                videoType,
                videoLabel,
                videoInfo,
                videoActor,
                videoActorInfo,
                dateFormat.format(date),
                videoPublish,
                videoPic
            )
            val actor = Actor(videoActor, videoActorInfo)
            viewModel.insertVideos(video, actor)

            Toast.makeText(BVMApplication.context, "修改成功", Toast.LENGTH_SHORT).show()
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