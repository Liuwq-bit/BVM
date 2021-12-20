package com.example.bvm.ui.video.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.*
import kotlin.concurrent.thread

class VideoViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()  // 观察影视列表
    private val searchByIdLiveData = MutableLiveData<String>()  // 观察由id标记查找的影视列表
    private val searchByRankLiveData = MutableLiveData<String>()
    private val markByIdLiveData = MutableLiveData<String>()    // 观察标记列表
//    private val markByRankLiveData = MutableLiveData<String>()
    private val commentByIdLiveData = MutableLiveData<String>() // 图书评论列表

    val videoList = ArrayList<Video>()
    val videoByIdList = ArrayList<Video>()
    val videoRankList = ArrayList<Video>()
    val markList = ArrayList<VideoMark>()
//    val markByRankList = ArrayList<VideoMark>()
    val commentList = ArrayList<VideoComment>()

    val videoLiveData = Transformations.switchMap(searchLiveData) { video_name ->
        Repository.searchAllVideo()
//        Repository.searchVideoByName(video_name)
    }

    val videoByIdLiveData = Transformations.switchMap(searchByIdLiveData) { user_id ->
        Repository.searchVideoByUserId(user_id)
    }

    val videoRankLiveData = Transformations.switchMap(searchByRankLiveData) { video_id ->
        Repository.searchVideoRank()
    }

    val markLiveData = Transformations.switchMap(markByIdLiveData) { user_id ->
        Repository.searchVideoMarkById(user_id)
    }

//    val rankMarkLiveData = Transformations.switchMap(markByRankLiveData) { user_id ->
//        Repository.searchVideoMarkByRank(user_id)
//    }

    val commentLiveData = Transformations.switchMap(commentByIdLiveData) { video_id ->
        Repository.searchVideoCommentByVideoId(video_id)
    }

    fun searchAllVideos() {
        searchLiveData.value = ""
    }

    fun searchAllMarkById(userId: String) {
        markByIdLiveData.value = userId
    }

    fun searchMarkVideoByUserId(user_id: String) {
        searchByIdLiveData.value = user_id
    }

    fun searchVideoRank() {
        searchByRankLiveData.value = ""
    }

//    fun searchVideoMarkByRank(userId: String) {
//        markByRankLiveData.value = userId
//    }

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

    fun searchVideoCommentByVideoId(video_id: String) {
        commentByIdLiveData.value = video_id
    }
}