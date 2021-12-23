package com.example.bvm.ui.book

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.logic.model.Author
import com.example.bvm.logic.model.Book
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.activity_book_change.*
import kotlinx.android.synthetic.main.book_add.*
import java.util.*

class BookChangeActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_change)
        setSupportActionBar(changeBookToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        val bookId = intent.getStringExtra(BookInfoActivity.BOOK_ID) ?: ""
        val bookTitle = intent.getStringExtra(BookInfoActivity.BOOK_TITLE) ?: ""
        val bookInfo = intent.getStringExtra(BookInfoActivity.BOOK_INFO) ?: ""
        val bookPic = intent.getStringExtra(BookInfoActivity.BOOK_PIC) ?: ""
        val bookAuthor = intent.getStringExtra(BookInfoActivity.BOOK_AUTHOR) ?: ""
        val bookAuthorInfo = intent.getStringExtra(BookInfoActivity.BOOK_AUTHOR_INFO) ?: ""
        val bookLabel = intent.getStringExtra(BookInfoActivity.BOOK_LABEL) ?: ""
        val bookPublishTime = intent.getStringExtra(BookInfoActivity.BOOK_PUBLISH_TIME) ?: ""

        changeBookNameText.editText?.setText(bookTitle)
        changeBookAuthorText.editText?.setText(bookAuthor)
        changeBookAuthorInfoText.editText?.setText(bookAuthorInfo)
        changeBookLabelText.editText?.setText(bookLabel)
        changeBookInfoText.editText?.setText(bookInfo)
        changeBookPublishTimeText.editText?.setText(bookPublishTime)
        changeBookPicText.editText?.setText(bookPic)

        changeBookCommitFab.setOnClickListener {
            val bookName = changeBookNameText.editText?.text.toString()
            val bookAuthor = changeBookAuthorText.editText?.text.toString()
            val bookAuthorInfo = changeBookAuthorInfoText.editText?.text.toString()
            val bookLabel = changeBookLabelText.editText?.text.toString()
            val bookInfo = changeBookInfoText.editText?.text.toString()
            val bookPublishTime = changeBookPublishTimeText.editText?.text.toString()
            val bookPic = changeBookPicText.editText?.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            val book = Book(bookId?.toLong(), bookName, bookLabel, bookInfo, bookAuthor, bookAuthorInfo, dateFormat.format(date), bookPublishTime, bookPic)
            val author = Author(bookAuthor, bookAuthorInfo)
            viewModel.insertBooks(book, author)

            Toast.makeText(BVMApplication.context, "修改成功", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}