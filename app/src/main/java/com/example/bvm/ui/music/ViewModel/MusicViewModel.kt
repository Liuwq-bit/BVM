package com.example.bvm.ui.music.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.Music
import com.example.bvm.logic.model.Singer
import com.example.bvm.logic.model.SingerOfMusic
import com.example.bvm.logic.model.Video
import kotlin.concurrent.thread

class MusicViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val musicList = ArrayList<Music>()

    val musicLiveData = Transformations.switchMap(searchLiveData) { music_name ->
        Repository.searchAllMusic()
//        Repository.searchMusicByName(music_name)
    }

    fun searchAllMusics() {
        searchLiveData.value = ""
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

}