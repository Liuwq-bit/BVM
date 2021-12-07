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
import com.example.bvm.R
import com.example.bvm.ui.music.Adapter.MusicAdapter
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.music_list.*

class MusicListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }

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
        adapter = MusicAdapter(this, viewModel.musicList)
        musicRecyclerView.adapter = adapter

        viewModel.musicLiveData.observe(viewLifecycleOwner, Observer { result ->
            val musics = result.getOrNull()
            if (musics != null) {
                viewModel.musicList.clear()
                viewModel.musicList.addAll(musics)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何音乐", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.searchAllMusics() // 显示所有音乐
    }
}