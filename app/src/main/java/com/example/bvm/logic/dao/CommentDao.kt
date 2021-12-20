package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bvm.logic.model.*

@Dao
interface BookCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookComment(bookComment: BookComment): Long

    @Query("select * from BookComment where book_id = :bookId")
    fun searchBookCommentByBookId(bookId: String): List<BookComment>

//    @Query("select mark.* from BookComment left join (select * from BookMark where user_id = :userId) as mark on BookComment.book_id = mark.book_id group by BookComment.book_id order by avg(rating) desc")
//    fun searchBookMarkByRank(userId: String): List<BookMark>

}

@Dao
interface VideoCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideoComment(videoComment: VideoComment): Long

    @Query("select * from VideoComment where video_id = :videoId")
    fun searchVideoCommentByVideoId(videoId: String): List<VideoComment>

//    @Query("select mark.* from VideoComment left join (select * from VideoMark where user_id = :userId) as mark on VideoComment.video_id = mark.video_id group by VideoComment.video_id order by avg(rating) desc")
//    fun searchVideoMarkByRank(userId: String): List<VideoMark>

}

@Dao
interface MusicCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicComment(musicComment: MusicComment): Long

    @Query("select * from MusicComment where music_id = :musicId")
    fun searchMusicCommentByMusicId(musicId: String): List<MusicComment>

}