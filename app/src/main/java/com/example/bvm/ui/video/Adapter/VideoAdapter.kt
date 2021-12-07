package com.example.bvm.ui.video.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.model.Video
import com.example.bvm.ui.video.VideoInfoActivity


class VideoAdapter(private val fragment: Fragment, private val videoList: List<Video>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoName: TextView = view.findViewById(R.id.itemTitle)
        val videoInfo: TextView = view.findViewById(R.id.itemInfo)
        val videoImage: ImageView = view.findViewById(R.id.itemImage)
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

        Glide.with(context).load(video.pic).into(holder.videoImage)
    }

    override fun getItemCount() = videoList.size
}
