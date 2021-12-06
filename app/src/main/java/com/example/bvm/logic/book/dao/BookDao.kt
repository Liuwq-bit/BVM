package com.example.bvm.logic.book.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.book.model.Book

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>

    @Query("select * from Book where book_name like '%' || :book_name || '%'")  // 使用双竖杠进行拼接
    fun searchBooks(book_name: String): List<Book>
}