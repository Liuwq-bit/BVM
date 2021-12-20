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
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.ui.book.Adapter.BookAdapter
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.book_rank_list.*
import kotlinx.android.synthetic.main.mark_book_list.*
import kotlin.Exception
import kotlin.concurrent.thread

class BookRankListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }

    private lateinit var adapter: BookAdapter

    companion object {  // 该fragment的标签
        const val type = "图书"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_rank_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        rankBookRecyclerView.layoutManager = layoutManager
        adapter = BookAdapter(this, viewModel.bookRankList, viewModel.markList)
        rankBookRecyclerView.adapter = adapter

        rankBookListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        rankBookListSwipeRefresh.setOnRefreshListener {
            reFreshBooks(adapter)
        }

        viewModel.bookRankLiveData.observe(viewLifecycleOwner, Observer { result ->
            val books = result.getOrNull()
            if (books != null) {
                viewModel.bookRankList.clear()
                viewModel.bookRankList.addAll(books)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "暂无图书排名", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.markLiveData.observe(viewLifecycleOwner, Observer { result ->
            val marks = result.getOrNull()
            if (marks != null) {
                viewModel.markList.clear()
                viewModel.markList.addAll(marks)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.searchBookRank()
        viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
    }

    private fun reFreshBooks(adapter: BookAdapter) {
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {
                viewModel.searchBookRank()
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
                adapter.notifyDataSetChanged()
                rankBookListSwipeRefresh.isRefreshing = false
            }
        }
    }

}