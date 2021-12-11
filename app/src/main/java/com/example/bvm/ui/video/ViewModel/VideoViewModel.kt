package com.example.bvm.ui.video.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.Actor
import com.example.bvm.logic.model.ActorOfVideo
import com.example.bvm.logic.model.Book
import com.example.bvm.logic.model.Video
import kotlin.concurrent.thread

class VideoViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val videoList = ArrayList<Video>()

    val videoLiveData = Transformations.switchMap(searchLiveData) { video_name ->
        Repository.searchAllVideo()
//        Repository.searchVideoByName(video_name)
    }

    fun searchAllVideos() {
        searchLiveData.value = ""
    }

    fun searchVideoByName(video_name: String) {
        searchLiveData.value = video_name
    }

    fun insertVideos(video: Video, actor: Actor) {
        thread {
            val video_id = Repository.insertVideo(video)
            val actor_id = Repository.insertActor(actor)
            val tmp = ActorOfVideo(video_id, actor_id)
            Repository.insertActorOfVideo(tmp)
        }
    }

    fun deleteVideoById(video_id: Long) {
        thread {
            Repository.deleteVideoById(video_id)
        }
    }
}