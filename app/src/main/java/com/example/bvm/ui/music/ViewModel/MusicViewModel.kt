package com.example.bvm.ui.music.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.*
import kotlin.concurrent.thread

class MusicViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()  // 观察音乐列表
    private val markByIdLiveData = MutableLiveData<String>()    // 观察标记列表
    private val commentByIdLiveData = MutableLiveData<String>() // 音乐评论列表

    val musicList = ArrayList<Music>()
    val markList = ArrayList<MusicMark>()
    val commentList = ArrayList<MusicComment>()

    val musicLiveData = Transformations.switchMap(searchLiveData) { music_name ->
        Repository.searchAllMusic()
//        Repository.searchMusicByName(music_name)
    }

    val markLiveData = Transformations.switchMap(markByIdLiveData) { user_id ->
        Repository.searchMusicMarkById(user_id)
    }

    val commentLiveData = Transformations.switchMap(commentByIdLiveData) { music_id ->
        Repository.searchMusicCommentByMusicId(music_id)
    }

    fun searchAllMusics() {
        searchLiveData.value = ""
    }

    fun searchAllMarkById(userId: String) {
        markByIdLiveData.value = userId
    }

    fun searchMusicByName(music_name: String) {
        searchLiveData.value = music_name
    }

    fun insertMusics(music: Music, singer: Singer) {
        thread {
            val musicId = Repository.insertMusic(music)
            val singerId = Repository.insertSinger(singer)
            val tmp = SingerOfMusic(musicId, singerId)
            Repository.insertSingerOfMusic(tmp)
        }
    }

    fun deleteMusicById(music_id: Long) {
        thread {
            Repository.deleteMusicById(music_id)
        }
    }

    fun insertMusicMark(musicMark: MusicMark) {
        thread {
            Repository.insertMusicMark(musicMark)
        }
    }

    fun insertMusicComment(musicComment: MusicComment) {
        thread {
            Repository.insertMusicComment(musicComment)
        }
    }

    fun searchMusicCommentByMusicId(music_id: String) {
        commentByIdLiveData.value = music_id
    }

}