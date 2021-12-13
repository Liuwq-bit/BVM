package com.example.bvm.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bvm.R
import com.example.bvm.ui.book.Adapter.BookAdapter
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.book_list.*
import kotlin.concurrent.thread

/**
 * 书籍信息展示列表
 */
class BookListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }

    private lateinit var adapter: BookAdapter

    companion object {  // 该fragment的标签
        const val type =  "图书"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
//        val layoutManager = GridLayoutManager(activity, 2)
        bookRecyclerView.layoutManager = layoutManager
        adapter = BookAdapter(this, viewModel.bookList)
        bookRecyclerView.adapter = adapter

        bookListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        bookListSwipeRefresh.setOnRefreshListener {
            reFreshBooks(adapter)
        }

        viewModel.bookLiveData.observe(viewLifecycleOwner, Observer { result -> // 动态查询数据
            val books = result.getOrNull()
            if (books != null) {
//                bookRecyclerView.visibility = View.VISIBLE
                viewModel.bookList.clear()
                viewModel.bookList.addAll(books)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何书籍", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.searchAllBooks()  // 显示所有书籍

    }

    private fun reFreshBooks(adapter: BookAdapter) {
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {

                viewModel.searchAllBooks()  // 显示所有书籍

                adapter.notifyDataSetChanged()
                bookListSwipeRefresh.isRefreshing = false
            }
        }
    }
}