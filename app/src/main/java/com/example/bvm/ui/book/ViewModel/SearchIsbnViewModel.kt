package com.example.bvm.ui.book.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.Book

class SearchIsbnViewModel : ViewModel() {

//    private val searchLiveData = MutableLiveData<String>()
//
//    val bookList = ArrayList<Book>()
//
//    val bookLiveData = Transformations.switchMap(searchLiveData) { isbn ->
//        Repository.searchIsbn(isbn)
//    }
//
//
//    fun searchBooks(isbn: String) {
//        searchLiveData.value = isbn
//    }
//
}