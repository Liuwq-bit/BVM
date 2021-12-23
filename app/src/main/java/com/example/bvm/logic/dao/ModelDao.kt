package com.example.bvm.logic.dao

import androidx.room.*
import com.example.bvm.logic.model.Book
import com.example.bvm.logic.model.Music
import com.example.bvm.logic.model.Video


@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>

    @Query("select * from Book where book_name like '%' || :book_name || '%'")  // 使用双竖杠进行拼接
    fun searchBooks(book_name: String): List<Book>

    @Query("delete from Book where book_id = :book_id")
    fun deleteBookById(book_id: Long): Int

    @Query("select Book.* from Book inner join BookMark on BookMark.book_id = Book.book_id where BookMark.user_id = :user_id")
    fun searchBooksByUserId(user_id: String): List<Book>

    @Query("select Book.* from Book inner join BookComment on BookComment.book_id = Book.book_id group by Book.book_id order by avg(rating) desc")
    fun searchBookRank(): List<Book>

}

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: Video): Long

    @Query("select * from Video")
    fun loadAllVideos(): List<Video>

    @Query("select * from Video where video_name like '%' || :video_name || '%'")
    fun searchVideos(video_name: String): List<Video>

    @Query("delete from Video where video_id = :video_id")
    fun deleteVideoById(video_id: Long): Int

    @Query("select Video.* from Video inner join VideoMark on VideoMark.video_id = Video.video_id where VideoMark.user_id = :user_id")
    fun searchVideoByUserId(user_id: String): List<Video>

    @Query("select Video.* from Video inner join VideoComment on VideoComment.video_id = Video.video_id group by Video.video_id order by avg(rating) desc")
    fun searchVideoRank(): List<Video>
}

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(music: Music): Long

    @Query("select * from Music")
    fun loadAllMusics(): List<Music>

    @Query("select * from Music where music_name like '%' || :music_name || '%'")   // 使用双竖杠进行拼接
    fun searchMusics(music_name: String): List<Music>

    @Query("delete from Music where music_id = :music_id")
    fun deleteMusicById(music_id: Long): Int

    @Query("select Music.* from Music inner join MusicMark on MusicMark.music_id = Music.music_id where MusicMark.user_id = :user_id")
    fun searchMusicByUserId(user_id: String): List<Music>

    @Query("select Music.* from Music inner join MusicComment on MusicComment.music_id = Music.music_id group by Music.music_id order by avg(rating) desc")
    fun searchMusicRank(): List<Music>

}