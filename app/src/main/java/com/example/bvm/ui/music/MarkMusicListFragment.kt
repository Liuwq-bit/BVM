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
import kotlinx.android.synthetic.main.mark_music_list.*
import kotlin.concurrent.thread

class MarkMusicListFragment: Fragment() {

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
        return inflater.inflate(R.layout.mark_music_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        markMusicRecyclerView.layoutManager = layoutManager
        adapter = MusicAdapter(this, viewModel.musicByIdList, viewModel.markList)
        markMusicRecyclerView.adapter = adapter

        markMusicListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        markMusicListSwipeRefresh.setOnRefreshListener {
            reFreshMusics(adapter)
        }

        viewModel.musicByIdLiveData.observe(viewLifecycleOwner, Observer { result ->
            val musics = result.getOrNull()
            if (musics != null) {
                viewModel.musicByIdList.clear()
                viewModel.musicByIdList.addAll(musics)
            } else {
                Toast.makeText(activity, "未能查询到标记的音乐", Toast.LENGTH_SHORT).show()
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

        viewModel.searchMarkMusicByUserId(BVMApplication.USER?.user_id.toString())
        viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
    }

    private fun reFreshMusics(adapter: MusicAdapter) {
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {
                viewModel.searchMarkMusicByUserId(BVMApplication.USER?.user_id.toString())
                adapter.notifyDataSetChanged()
                markMusicListSwipeRefresh.isRefreshing = false
            }
        }
    }
}