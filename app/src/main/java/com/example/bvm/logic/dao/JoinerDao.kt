package com.example.bvm.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.Actor
import com.example.bvm.logic.model.Author
import com.example.bvm.logic.model.Singer

@Dao
interface AuthorDao {

    @Insert
    fun insertAuthor(author: Author): Long

    @Query("select * from Author")
    fun loadAllAuthor(): List<Author>

    @Query("select * from Author where author_id = :author_id")
    fun searchAuthorById(author_id: Long): List<Author>
}

@Dao
interface ActorDao {

    @Insert
    fun insertActor(actor: Actor): Long
}

@Dao
interface SingerDao {

    @Insert
    fun insertSinger(singer: Singer): Long
}