package com.example.bvm.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bvm.R
import com.example.bvm.ui.book.BookInfoActivity
import com.example.bvm.ui.music.Adapter.MusicCommentAdapter
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import kotlinx.android.synthetic.main.comment_list.*
import kotlin.concurrent.thread

class MusicCommentFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }

    private lateinit var adapter: MusicCommentAdapter

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
        adapter = MusicCommentAdapter(this, viewModel.commentList)
        commentRecyclerView.adapter = adapter

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
        viewModel.searchMusicCommentByMusicId(MusicInfoActivity.music_id)

        thread {
            Thread.sleep(100)
            activity?.runOnUiThread {
//                Toast.makeText(context, viewModel.commentList.size.toString(), Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }

    }
}