package com.example.bvm.ui.music.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.*
import kotlin.concurrent.thread

class MusicViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    private val markByIdLiveData = MutableLiveData<String>()

    val musicList = ArrayList<Music>()
    val markList = ArrayList<MusicMark>()

    val musicLiveData = Transformations.switchMap(searchLiveData) { music_name ->
        Repository.searchAllMusic()
//        Repository.searchMusicByName(music_name)
    }

    val markLiveData = Transformations.switchMap(markByIdLiveData) { user_id ->
        Repository.searchMusicMarkById(user_id)
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
            val music_id = Repository.insertMusic(music)
            val singer_id = Repository.insertSinger(singer)
            val tmp = SingerOfMusic(music_id, singer_id)
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

}