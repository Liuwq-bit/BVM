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

    val bookList = ArrayList<Book>()

    val bookLiveData = Transformations.switchMap(searchLiveData) { book_name ->
        Repository.searchAllBook()
    }

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
}