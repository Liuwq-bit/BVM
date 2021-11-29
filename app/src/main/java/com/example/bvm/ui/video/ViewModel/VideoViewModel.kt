package com.example.bvm.ui.video.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.book.model.Book
import kotlin.concurrent.thread

class VideoViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val videoList = ArrayList<Book>()

    val videoLiveData = Transformations.switchMap(searchLiveData) { title ->
        // todo change item
        Repository.searchAllBook()
//        Repository.searchBookByTitle(title)
    }

    // todo
    fun searchAllBooks() {
        searchLiveData.value = ""
    }

    // todo
    fun searchBookByTitle(title: String) {
        searchLiveData.value = title
    }

    // todo
    fun insertBooks(book: Book) {
        thread {
            Repository.insertBook(book)
        }
    }
}