package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.Author

@Dao
interface AuthorDao {

    @Insert
    fun insertAuthor(author: Author): Long

    @Query("select * from Author")
    fun loadAllAuthor(): List<Author>

    @Query("select * from Author where author_name like '%' || :author_name || '%'")
    fun searchAuthor(author_name: String): List<Author>
}