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
import com.example.bvm.logic.model.BookComment
import com.example.bvm.ui.book.Adapter.BookCommentAdapter
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.comment_list.*
import kotlin.concurrent.thread

class BookCommentFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }

    private lateinit var adapter: BookCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        commentRecyclerView.layoutManager = layoutManager
        adapter = BookCommentAdapter(this, viewModel.commentList)
        commentRecyclerView.adapter = adapter

//        val List = ArrayList<BookComment>()
//        List.add(BookComment(1, 1, "测试评论1", 4.5f, "2020-1"))
//        List.add(BookComment(1, 1, "测试评论2", 4.5f, "2020-1"))
//        adapter = BookCommentAdapter(this, List)


        viewModel.commentLiveData.observe(viewLifecycleOwner, Observer { result ->
            val comments = result.getOrNull()
            if (comments != null) {
                viewModel.commentList.clear()
                viewModel.commentList.addAll(comments)
//                Toast.makeText(context, "装载完成", Toast.LENGTH_SHORT).show()
            } else {
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.searchBookCommentByBookId(BookInfoActivity.book_id)


        thread {
            Thread.sleep(100)
            activity?.runOnUiThread {
//                Toast.makeText(context, viewModel.commentList.size.toString(), Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }
    }
}