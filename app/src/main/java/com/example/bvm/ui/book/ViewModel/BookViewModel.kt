package com.example.bvm.ui.book.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.book.model.Book
import kotlin.concurrent.thread

class BookViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val bookList = ArrayList<Book>()

    val bookLiveData = Transformations.switchMap(searchLiveData) { bookName ->
        Repository.searchAll()
    }

    fun searchAllBooks(bookName: String) {
        searchLiveData.value = bookName
    }

    fun insertBooks(book: Book) {
        thread {
            Repository.insertBook(book)
        }
    }
}