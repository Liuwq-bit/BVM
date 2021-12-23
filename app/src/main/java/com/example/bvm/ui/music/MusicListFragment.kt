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
import com.example.bvm.ui.video.Adapter.VideoAdapter
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.book_list.*
import kotlinx.android.synthetic.main.music_list.*
import kotlin.concurrent.thread

class MusicListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }
    var tag1 = true
    var tag2 = true

    private lateinit var adapter: MusicAdapter

    companion object {
        const val type = "音乐"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.music_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        musicRecyclerView.layoutManager = layoutManager
        adapter = MusicAdapter(this, viewModel.musicList, viewModel.markList)
        musicRecyclerView.adapter = adapter

        musicListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        musicListSwipeRefresh.setOnRefreshListener {
            reFreshMusics(adapter)
        }

        viewModel.musicLiveData.observe(viewLifecycleOwner, Observer { result ->
            val musics = result.getOrNull()
            if (musics != null) {
                viewModel.musicList.clear()
                viewModel.musicList.addAll(musics)
                if (tag1) {
                    adapter.notifyDataSetChanged()
                    tag1 = false
                }
            } else {
                Toast.makeText(activity, "未能查询到任何音乐", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.markLiveData.observe(viewLifecycleOwner, Observer { result ->
            val marks = result.getOrNull()
            if (marks != null) {
                viewModel.markList.clear()
                viewModel.markList.addAll(marks)
                if (tag2) {
                    adapter.notifyDataSetChanged()
                    tag2 = false
                }
            }
        })

        viewModel.searchAllMusics() // 显示所有音乐
        viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
    }

    private fun reFreshMusics(adapter: MusicAdapter) {
        thread {
            activity?.runOnUiThread {
                viewModel.searchAllMusics()
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
            }
            Thread.sleep(2000)
            activity?.runOnUiThread {
                viewModel.musicList.shuffle()
                adapter.notifyDataSetChanged()
                musicListSwipeRefresh.isRefreshing = false
            }
        }
    }

}