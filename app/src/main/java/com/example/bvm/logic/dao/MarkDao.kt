package com.example.bvm.logic.dao

import androidx.room.*
import com.example.bvm.logic.model.BookMark
import com.example.bvm.logic.model.MusicMark
import com.example.bvm.logic.model.VideoMark

@Dao
interface BookMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookMark(bookMark: BookMark): Long

    // 查找当前用户标记过的图书数据，0，1，2分别为想看、在看、看过
    @Query("select * from BookMark where user_id = :userId and type = :type")
    fun searchBookMarkByIdType(userId: String, type: String): List<BookMark>

    @Delete
    fun deleteBookMark(bookMark: BookMark)

    // 查询某本书的标记信息
    @Query("select * from BookMark where user_id = :userId")
    fun searchBookMarkById(userId: String): List<BookMark>

    @Query("SELECT * from BookMark WHERE user_id = :userId and book_id = :bookId")
    fun getItemById(userId: Long, bookId: Long): List<BookMark>

    @Query("UPDATE BookMark SET type = :type WHERE user_id = :userId and book_id = :bookId")
    fun updateBookMark(userId: Long, bookId: Long, type: Int)

    fun insertOrUpdateBookMark(bookMark: BookMark) {
        val itemsFromDB: List<BookMark> = getItemById(bookMark.user_id, bookMark.book_id)
        if (itemsFromDB.isEmpty() || itemsFromDB[0].type != bookMark.type)
            insertBookMark(bookMark)
        else
            deleteBookMark(bookMark)
    }
}

@Dao
interface VideoMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideoMark(videoMark: VideoMark): Long

    // 查找当前用户标记过的影视数据
    @Query("select * from VideoMark where user_id = :userId and type = :type")
    fun searchVideoMarkByIdType(userId: String, type: String): List<VideoMark>

    @Delete
    fun deleteVideoMark(videoMark: VideoMark)

    // 查询某影视的标记信息
    @Query("select * from VideoMark where user_id = :userId")
    fun searchVideoMarkById(userId: String): List<VideoMark>

    @Query("SELECT * from VideoMark WHERE user_id = :userId and video_id = :videoId")
    fun getItemById(userId: Long, videoId: Long): List<VideoMark>

    @Query("UPDATE VideoMark SET type = :type WHERE user_id = :userId and video_id = :videoId")
    fun updateVideoMark(userId: Long, videoId: Long, type: Int)

    fun insertOrUpdateVideoMark(videoMark: VideoMark) {
        val itemsFromDB: List<VideoMark> = getItemById(videoMark.user_id, videoMark.video_id)
        if (itemsFromDB.isEmpty() || itemsFromDB[0].type != videoMark.type)
            insertVideoMark(videoMark)
        else
            deleteVideoMark(videoMark)
    }
}

@Dao
interface MusicMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicMark(musicMark: MusicMark): Long

    // 查找当前用户标记过的音乐数据
    @Query("select * from MusicMark where user_id = :userId and type = :type")
    fun searchMusicMarkByIdType(userId: String, type: String): List<MusicMark>

    @Delete
    fun deleteMusicMark(musicMark: MusicMark)

    // 查询某音乐的标记信息
    @Query("select * from MusicMark where user_id = :userId")
    fun searchMusicMarkById(userId: String): List<MusicMark>

    @Query("SELECT * from MusicMark WHERE user_id = :userId and music_id = :musicId")
    fun getItemById(userId: Long, musicId: Long): List<MusicMark>

    @Query("UPDATE MusicMark SET type = :type WHERE user_id = :userId and music_id = :musicId")
    fun updateMusicMark(userId: Long, musicId: Long, type: Int)

    fun insertOrUpdateMusicMark(musicMark: MusicMark) {
        val itemsFromDB: List<MusicMark> = getItemById(musicMark.user_id, musicMark.music_id)
        if (itemsFromDB.isEmpty() || itemsFromDB[0].type != musicMark.type)
            insertMusicMark(musicMark)
        else
            deleteMusicMark(musicMark)
    }
}