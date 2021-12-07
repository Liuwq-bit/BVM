package com.example.bvm.ui.video.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
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

    fun insertVideos(video: Video) {
        thread {
            Repository.insertVideo(video)
        }
    }
}