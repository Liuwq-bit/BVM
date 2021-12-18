package com.example.bvm.ui.book.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.*
import kotlin.concurrent.thread

class BookViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()  // 观察图书列表
    private val markByIdLiveData = MutableLiveData<String>()    // 观察标记列表
    private val commentByIdLiveData = MutableLiveData<String>() // 图书评论列表

    val bookList = ArrayList<Book>()
    val markList = ArrayList<BookMark>()
    val commentList = ArrayList<BookComment>()

    val bookLiveData = Transformations.switchMap(searchLiveData) { book_name ->
        Repository.searchAllBook()
    }

    val markLiveData = Transformations.switchMap(markByIdLiveData) { user_id ->
        Repository.searchBookMarkById(user_id)
    }

    val commentLiveData = Transformations.switchMap(commentByIdLiveData) { book_id ->
        Repository.searchBookCommentByBookId(book_id)
    }

    fun searchAllBooks() {
        searchLiveData.value = ""
    }

    fun searchAllMarkById(userId: String) {
        markByIdLiveData.value = userId
    }

    fun searchBookByTitle(title: String) {
        searchLiveData.value = title
    }

    fun insertBooks(book: Book, author: Author) {
        thread {
            val bookId = Repository.insertBook(book)
            val authorId = Repository.insertAuthor(author)
            val tmp = AuthorOfBook(bookId, authorId)
            Repository.insertAuthorOfBook(tmp)
        }
    }

    fun deleteBookById(book_id: Long) {
        thread {
            Repository.deleteBookById(book_id)
        }
    }

    fun insertBookMark(bookMark: BookMark) {
        thread {
            Repository.insertBookMark(bookMark)
        }
    }

    fun insertBookComment(bookComment: BookComment) {
        thread {
            Repository.insertBookComment(bookComment)
        }
    }

    fun searchBookCommentByBookId(book_id: String) {
        commentByIdLiveData.value = book_id
    }
}