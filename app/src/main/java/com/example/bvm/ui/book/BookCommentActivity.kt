package com.example.bvm.ui.book

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.logic.model.BookComment
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.activity_book_comment.*
import java.util.*

class BookCommentActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }
    var rating: Float = 2.5F

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_comment)
        setSupportActionBar(bookCommentToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
        val bookId = intent.getStringExtra(BookInfoActivity.BOOK_ID) ?: "0"

        bookRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
//            Toast.makeText(this, "rating: $rating", Toast.LENGTH_SHORT).show()
            this.rating = rating
//            Toast.makeText(BVMApplication.context, "$bookId", Toast.LENGTH_SHORT).show()
        }

        bookRatingCommentFab.setOnClickListener {
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val comment = bookCommentText.editText?.text.toString()
            viewModel.insertBookComment(BookComment(bookId.toLong(), BVMApplication.USER?.user_id, comment, rating, dateFormat.format(date)))
//            Toast.makeText(BVMApplication.context, "$bookId, $comment, $rating, ${dateFormat.format(date)}", Toast.LENGTH_SHORT).show()
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