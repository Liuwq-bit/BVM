package com.example.bvm.ui.music

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
import com.example.bvm.ui.music.Adapter.MusicAdapter
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import kotlinx.android.synthetic.main.music_rank_list.*
import kotlin.concurrent.thread


class MusicRankListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }

    private lateinit var adapter: MusicAdapter

    companion object {  // 该fragment的标签
        const val type = "音乐"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.music_rank_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        rankMusicRecyclerView.layoutManager = layoutManager
        adapter = MusicAdapter(this, viewModel.musicRankList, viewModel.markList)
        rankMusicRecyclerView.adapter = adapter

        rankMusicListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        rankMusicListSwipeRefresh.setOnRefreshListener {
            reFreshBooks(adapter)
        }

        viewModel.musicRankLiveData.observe(viewLifecycleOwner, Observer { result ->
            val books = result.getOrNull()
            if (books != null) {
                viewModel.musicRankList.clear()
                viewModel.musicRankList.addAll(books)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "暂无音乐排名", Toast.LENGTH_SHORT).show()
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

        viewModel.searchMusicRank()
        viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
    }

    private fun reFreshBooks(adapter: MusicAdapter) {
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {
                viewModel.searchMusicRank()
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
                adapter.notifyDataSetChanged()
                rankMusicListSwipeRefresh.isRefreshing = false
            }
        }
    }

}