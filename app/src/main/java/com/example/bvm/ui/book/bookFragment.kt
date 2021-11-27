package com.example.bvm.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bvm.R
import com.example.bvm.logic.book.model.Book
import com.example.bvm.ui.book.Adapter.BookAdapter
import com.example.bvm.ui.book.ViewModel.BookViewModel
import com.example.bvm.ui.book.ViewModel.SearchIsbnViewModel
import kotlinx.android.synthetic.main.fragment_book.*

/**
 * 图书查询页面
 */
class bookFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }

    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = BookAdapter(this, viewModel.bookList)
        recyclerView.adapter = adapter

        val book1 = Book("测试1", "2021.11.27", "这是一个测试用书", "测试",
            "https://ceshi.com", "www.douban.com", "9.0")
        viewModel.insertBooks(book1)
        val book2 = Book("测试2", "2021.11.27", "这也是一个测试用书", "测试",
            "https://ceshi.com", "www.douban.com", "9.0")
        viewModel.insertBooks(book2)



        searchBookEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchAllBooks(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.bookList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.bookLiveData.observe(viewLifecycleOwner, Observer { result -> // 动态查询数据
            val books = result.getOrNull()
            if (books != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.bookList.clear()
                viewModel.bookList.addAll(books)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何书籍", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}