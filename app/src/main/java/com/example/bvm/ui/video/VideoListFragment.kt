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
import com.example.bvm.ui.book.Adapter.BookAdapter
import com.example.bvm.ui.video.Adapter.VideoAdapter
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import kotlinx.android.synthetic.main.book_list.*
import kotlinx.android.synthetic.main.video_list.*
import kotlin.concurrent.thread


class VideoListFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }

    private lateinit var adapter: VideoAdapter

    companion object {  // 该fragment的标签
        const val type =  "影视"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
//        val layoutManager = GridLayoutManager(activity, 2)
        videoRecyclerView.layoutManager = layoutManager
        adapter = VideoAdapter(this, viewModel.videoList, viewModel.markList)
        videoRecyclerView.adapter = adapter

        videoListSwipeRefresh.setColorSchemeResources(R.color.cardview_shadow_end_color)
        videoListSwipeRefresh.setOnRefreshListener {
            reFreshVideos(adapter)
        }

//        val book1 = Book("测试3", "2021.11.27", "这是一个测试用书", "测试",
//            "https://ceshi.com", "www.douban.com", "9.0")
//        viewModel.insertBooks(book1)
//        val book2 = Book("测试4", "2021.11.27", "这也是一个测试用书", "测试",
//            "https://ceshi.com", "www.douban.com", "9.0")
//        viewModel.insertBooks(book2)


        viewModel.videoLiveData.observe(viewLifecycleOwner, Observer { result -> // 动态查询数据
            val videos = result.getOrNull()
            if (videos != null) {
                viewModel.videoList.clear()
                viewModel.videoList.addAll(videos)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何影视", Toast.LENGTH_SHORT).show()
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

        viewModel.searchAllVideos()  // 显示所有书籍
        viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())

    }

    private fun reFreshVideos(adapter: VideoAdapter) {
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {

                viewModel.searchAllVideos()
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
                adapter.notifyDataSetChanged()
                videoListSwipeRefresh.isRefreshing = false
            }
        }
    }

}