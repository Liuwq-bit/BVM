package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.Video


@Dao
interface VideoDao {

    @Insert
    fun insertVideo(video: Video): Long

    @Query("select * from Video")
    fun loadAllVideos(): List<Video>

    @Query("select * from Video where video_name like '%' || :video_name || '%'")
    fun searchVideos(video_name: String): List<Video>
}