package com.example.bvm.ui.book

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.R
import com.example.bvm.logic.model.Author
import com.example.bvm.logic.model.Book
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.book_add.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*

/**
 * 书籍信息添加
 */
class BookAddFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bookCommitFab.setOnClickListener {
            // todo 增加格式判断机制
            val bookName = bookNameText.editText?.text.toString()
            val bookAuthor = bookAuthorText.editText?.text.toString()
            val bookAuthorInfo = bookAuthorInfoText.editText?.text.toString()
            val bookLabel = bookLabelText.editText?.text.toString()
            val bookInfo = bookInfoText.editText?.text.toString()
            val bookPublishTime = bookPublishTimeText.editText?.text.toString()
            val bookPic = bookPicText.editText?.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

//            val book = Book(bookName, bookLabel, bookInfo, dateFormat.format(date), bookPublishTime, bookPic)
            val book = Book(null, bookName, bookLabel, bookInfo, bookAuthor, bookAuthorInfo, dateFormat.format(date), bookPublishTime, bookPic)
            val author = Author(bookAuthor, bookAuthorInfo)
            viewModel.insertBooks(book, author)

            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
            activity?.finish()
        }
    }

}