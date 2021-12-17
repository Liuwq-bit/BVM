package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bvm.logic.model.BookComment
import com.example.bvm.logic.model.MusicComment
import com.example.bvm.logic.model.VideoComment

@Dao
interface BookCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookComment(bookComment: BookComment): Long

    @Query("select * from BookComment where book_id = :bookId")
    fun searchBookCommentByBookId(bookId: String): List<BookComment>

}

@Dao
interface VideoCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideoComment(videoComment: VideoComment): Long

    @Query("select * from VideoComment where video_id = :videoId")
    fun searchVideoCommentByVideoId(videoId: String): List<VideoComment>

}

@Dao
interface MusicCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicComment(musicComment: MusicComment): Long

    @Query("select * from MusicComment where music_id = :musicId")
    fun searchMusicCommentByMusicId(musicId: String): List<MusicComment>

}