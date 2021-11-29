package com.example.bvm.ui.video.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.book.model.Book
import com.example.bvm.ui.video.VideoInfoActivity


class VideoAdapter(private val fragment: Fragment, private val videoList: List<Book>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoName: TextView = view.findViewById(R.id.itemTitle)
        val videoInfo: TextView = view.findViewById(R.id.itemInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item,
            parent, false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val video = videoList[position]
            val intent = Intent(context, VideoInfoActivity::class.java).apply {
                putExtra(VideoInfoActivity.VIDEO_TITLE, video.title)
                putExtra(VideoInfoActivity.VIDEO_INFO, video.bookInfo)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        if (video.title.length > 9) {
            val shortTitle = video.title.substring(0, 9) + " ..."
            holder.videoName.text = shortTitle
        } else
            holder.videoName.text = video.title

        if (video.bookInfo.length > 30) {
            val shortInfo = video.bookInfo.substring(0, 29) + " ..."
            holder.videoInfo.text = shortInfo
        } else
            holder.videoInfo.text = video.bookInfo
    }

    override fun getItemCount() = videoList.size
}
