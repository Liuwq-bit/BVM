package com.example.bvm.logic

import android.content.Context
import android.util.Log
import androidx.lifecycle.liveData
import com.example.bvm.BVMApplication
import com.example.bvm.logic.book.db.BookDatabase
import com.example.bvm.logic.book.model.Book
import com.example.bvm.logic.book.model.BookResponse
import com.example.bvm.logic.book.network.BookNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.concurrent.thread

/*
    仓库类，判断数据源调用
 */

object Repository {

//    fun searchIsbn(isbn: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            val isbnResponse = BookNetwork.searchIsbn(isbn)
//            if (isbnResponse != null) {
//                val books = isbnResponse.books
//                Result.success(books)
//            } else {
//                Result.failure(RuntimeException("response message is ${isbnResponse.message}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Book>>(e)
//        }
//        emit(result)
//    }

    // 插入数据并返回id
    fun insertBook(book: Book): Long {
        return BookDatabase.getDatabase(BVMApplication.context).bookDao().insertBook(book)
    }

    // 搜索全部图书数据
    fun searchAllBook() = liveData(Dispatchers.IO) {
        val result = try {
            val bookResponse = BookDatabase.getDatabase(BVMApplication.context).bookDao().loadAllBooks()
//            Log.d("MainActivity", bookResponse[0].title)
            Result.success(bookResponse)
        } catch (e: Exception) {
            Result.failure<List<Book>>(e)
        }
        emit(result)
    }

    // 搜索全部图书数据
    fun searchBookByTitle(title: String) = liveData(Dispatchers.IO) {
        val result = try {
            val bookResponse = BookDatabase.getDatabase(BVMApplication.context).bookDao().searchBooks(title)
            Result.success(bookResponse)
        } catch (e: Exception) {
            Result.failure<List<Book>>(e)
        }
        emit(result)
    }


}