package com.example.bvm.ui.video.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.model.Video
import com.example.bvm.ui.video.VideoInfoActivity
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text
import kotlin.concurrent.thread


class VideoAdapter(private val fragment: Fragment, private val videoList: List<Video>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    val viewModel by lazy { ViewModelProviders.of(fragment).get(VideoViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoName: TextView = view.findViewById(R.id.itemTitle)
        val videoInfo: TextView = view.findViewById(R.id.itemInfo)
        val videoActor: TextView = view.findViewById(R.id.authorText)
        val videoImage: ImageView = view.findViewById(R.id.itemImage)
        val deleteBtn: MaterialButton = view.findViewById(R.id.deleteItemBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val video = videoList[position]
            val intent = Intent(context, VideoInfoActivity::class.java).apply {
                putExtra(VideoInfoActivity.VIDEO_TITLE, video.video_name)
                putExtra(VideoInfoActivity.VIDEO_INFO, video.info)
                putExtra(VideoInfoActivity.VIDEO_IMAGE, video.pic)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        if (video.video_name.length > 9) {
            val shortTitle = video.video_name.substring(0, 9) + " ..."
            holder.videoName.text = shortTitle
        } else
            holder.videoName.text = video.video_name

        if (video.info.length > 30) {
            val shortInfo = video.info.substring(0, 29) + " ..."
            holder.videoInfo.text = shortInfo
        } else
            holder.videoInfo.text = video.info
        holder.videoActor.text = video.actor

        Glide.with(context).load(video.pic).into(holder.videoImage)

        holder.deleteBtn.setOnClickListener {
            viewModel.deleteVideoById(video.video_id)

            viewModel.videoLiveData.observe(fragment.viewLifecycleOwner, Observer { result -> // 动态查询数据
                val videos = result.getOrNull()
                if (videos != null) {
                    viewModel.videoList.clear()
                    viewModel.videoList.addAll(videos)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "未能查询到任何书籍", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            })

            reFreshVideo()
        }

    }

    override fun getItemCount() = videoList.size

    private fun reFreshVideo() {
        thread {
            Thread.sleep(500)
            fragment.activity?.runOnUiThread {
                viewModel.searchAllVideos()
                notifyDataSetChanged()
            }
        }
    }
}
