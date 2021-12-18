package com.example.bvm.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.R
import com.example.bvm.logic.model.Actor
import com.example.bvm.logic.model.Video
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.video_add.*
import java.text.SimpleDateFormat
import java.util.*

class VideoAddFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        videoCommitFab.setOnClickListener {
            // todo 增加格式判断机制
            val videoName = videoNameText.editText?.text.toString()
            val videoType = videoTypeText.editText?.text.toString()
            val videoActor = videoActorText.editText?.text.toString()
            val videoActorInfo = videoActorInfoText.editText?.text.toString()
            val videoLabel = videoLabelText.editText?.text.toString()
            val videoInfo = videoInfoText.editText?.text.toString()
            val videoPublish = videoPublishTimeText.editText?.text.toString()
            val videoPic = videoPicText.editText?.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            val video = Video(videoName, videoType, videoLabel, videoInfo, videoActor, videoActorInfo, dateFormat.format(date), videoPublish, videoPic)
            val actor = Actor(videoActor, videoActorInfo)
            viewModel.insertVideos(video, actor)

            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
            activity?.finish()
        }
    }
}