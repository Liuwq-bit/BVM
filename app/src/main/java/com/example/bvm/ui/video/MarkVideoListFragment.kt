package com.example.bvm.ui.video

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
import com.example.bvm.ui.video.Adapter.VideoAdapter
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.mark_video_list.*
import kotlin.concurrent.thread

class MarkVideoListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }

    private lateinit var adapter: VideoAdapter

    companion object {  // 该fragment的标签
        const val type = "影视"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mark_video_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        markVideoRecyclerView.layoutManager = layoutManager
        adapter = VideoAdapter(this, viewModel.videoByIdList, viewModel.markList)
        markVideoRecyclerView.adapter = adapter

        markVideoListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        markVideoListSwipeRefresh.setOnRefreshListener {
            reFreshVideos(adapter)
        }

        viewModel.videoByIdLiveData.observe(viewLifecycleOwner, Observer { result ->
            val videos = result.getOrNull()
            if (videos != null) {
                viewModel.videoByIdList.clear()
                viewModel.videoByIdList.addAll(videos)
            } else {
                Toast.makeText(activity, "未能查询到标记的影视", Toast.LENGTH_SHORT).show()
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

        viewModel.searchMarkVideoByUserId(BVMApplication.USER?.user_id.toString())
        viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
    }

    private fun reFreshVideos(adapter: VideoAdapter) {
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {
                viewModel.searchMarkVideoByUserId(BVMApplication.USER?.user_id.toString())
                adapter.notifyDataSetChanged()
                markVideoListSwipeRefresh.isRefreshing = false
            }
        }
    }
}