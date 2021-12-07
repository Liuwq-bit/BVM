package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.Music

@Dao
interface MusicDao {

    @Insert
    fun insertMusic(music: Music): Long

    @Query("select * from Music")
    fun loadAllMusics(): List<Music>

    @Query("select * from Music where music_name like '%' || :music_name || '%'")   // 使用双竖杠进行拼接
    fun searchMusics(music_name: String): List<Music>

}