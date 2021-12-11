package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.ActorOfVideo
import com.example.bvm.logic.model.AuthorOfBook
import com.example.bvm.logic.model.SingerOfMusic

@Dao
interface AuthorOfBookDao {

    @Insert
    fun insertAuthorOfBook(authorOfBook: AuthorOfBook): Long

    @Query("select author_id from AuthorOfBook where book_id = :book_id")
    fun searchAuthorByBookId(book_id: String): Long
}

@Dao
interface ActorOfVideoDao {

    @Insert
    fun insertActorOfVideo(actorOfVideo: ActorOfVideo): Long
}

@Dao
interface SingerOfMusicDao {

    @Insert
    fun insertSingerOfMusic(singerOfMusic: SingerOfMusic): Long
}