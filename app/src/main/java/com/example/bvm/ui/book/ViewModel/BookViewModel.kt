package com.example.bvm.ui.book.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.Author
import com.example.bvm.logic.model.AuthorOfBook
import com.example.bvm.logic.model.Book
import kotlin.concurrent.thread

class BookViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
//    private val searchAuthorLiveData = MutableLiveData<String>()

    val bookList = ArrayList<Book>()
//    val authorList = ArrayList<Author>()

    val bookLiveData = Transformations.switchMap(searchLiveData) { book_name ->
        Repository.searchAllBook()
//        Repository.searchBookByTitle(book_name)
    }

//    val authorLiveData = Transformations.switchMap(searchAuthorLiveData) { book_id ->
//        Repository.searchAuthorByBookId(book_id)
//    }

    fun searchAllBooks() {
        searchLiveData.value = ""
    }

    fun searchBookByTitle(title: String) {
        searchLiveData.value = title
    }

    fun insertBooks(book: Book, author: Author) {
        thread {
            val book_id = Repository.insertBook(book)
            val author_id = Repository.insertAuthor(author)
            val tmp = AuthorOfBook(book_id, author_id)
            Repository.insertAuthorOfBook(tmp)
        }
    }

    fun deleteBookById(book_id: Long) {
        thread {
            Repository.deleteBookById(book_id)
        }
    }

//    fun searchAuthorByBookId(book_id: String) {
//        searchAuthorLiveData.value = book_id
//    }
}