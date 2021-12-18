package com.example.bvm.ui.video.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.*
import kotlin.concurrent.thread

class VideoViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()  // 观察影视列表
    private val markByIdLiveData = MutableLiveData<String>()    // 观察标记列表

    val videoList = ArrayList<Video>()
    val markList = ArrayList<VideoMark>()

    val videoLiveData = Transformations.switchMap(searchLiveData) { video_name ->
        Repository.searchAllVideo()
//        Repository.searchVideoByName(video_name)
    }

    val markLiveData = Transformations.switchMap(markByIdLiveData) { user_id ->
        Repository.searchVideoMarkById(user_id)
    }

    fun searchAllVideos() {
        searchLiveData.value = ""
    }

    fun searchAllMarkById(userId: String) {
        markByIdLiveData.value = userId
    }

    fun searchVideoByName(video_name: String) {
        searchLiveData.value = video_name
    }

    fun insertVideos(video: Video, actor: Actor) {
        thread {
            val videoId = Repository.insertVideo(video)
            val actorId = Repository.insertActor(actor)
            val tmp = ActorOfVideo(videoId, actorId)
            Repository.insertActorOfVideo(tmp)
        }
    }

    fun deleteVideoById(video_id: Long) {
        thread {
            Repository.deleteVideoById(video_id)
        }
    }

    fun insertVideoMark(videoMark: VideoMark) {
        thread {
            Repository.insertVideoMark(videoMark)
        }
    }

    fun insertVideoComment(videoComment: VideoComment) {
        thread {
            Repository.insertVideoComment(videoComment)
        }
    }
}