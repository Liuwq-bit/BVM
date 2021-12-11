package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.Book
import com.example.bvm.logic.model.Music
import com.example.bvm.logic.model.Video

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>

    @Query("select * from Book where book_name like '%' || :book_name || '%'")  // 使用双竖杠进行拼接
    fun searchBooks(book_name: String): List<Book>

    @Query("delete from Book where book_id = :book_id")
    fun deleteBookById(book_id: Long): Int

}

@Dao
interface VideoDao {

    @Insert
    fun insertVideo(video: Video): Long

    @Query("select * from Video")
    fun loadAllVideos(): List<Video>

    @Query("select * from Video where video_name like '%' || :video_name || '%'")
    fun searchVideos(video_name: String): List<Video>

    @Query("delete from Video where video_id = :video_id")
    fun deleteVideoById(video_id: Long): Int
}

@Dao
interface MusicDao {

    @Insert
    fun insertMusic(music: Music): Long

    @Query("select * from Music")
    fun loadAllMusics(): List<Music>

    @Query("select * from Music where music_name like '%' || :music_name || '%'")   // 使用双竖杠进行拼接
    fun searchMusics(music_name: String): List<Music>

    @Query("delete from Music where music_id = :music_id")
    fun deleteMusicById(music_id: Long): Int

}