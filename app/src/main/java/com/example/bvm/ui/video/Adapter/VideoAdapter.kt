package com.example.bvm.ui.video.Adapter

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.model.BookMark
import com.example.bvm.logic.model.Video
import com.example.bvm.logic.model.VideoMark
import com.example.bvm.ui.video.VideoInfoActivity
import com.example.bvm.ui.video.ViewModel.VideoViewModel
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text
import java.util.*
import kotlin.concurrent.thread


class VideoAdapter(private val fragment: Fragment, private val videoList: List<Video>, private val markList: List<VideoMark>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    val viewModel by lazy { ViewModelProviders.of(fragment).get(VideoViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoName: TextView = view.findViewById(R.id.itemTitle)
        val videoInfo: TextView = view.findViewById(R.id.itemInfo)
        val videoActor: TextView = view.findViewById(R.id.authorText)
        val videoImage: ImageView = view.findViewById(R.id.itemImage)
        val deleteBtn: MaterialButton = view.findViewById(R.id.deleteItemBtn)
        val videoTypeBtn0: MaterialButton = view.findViewById(R.id.typeBtn0)
        val videoTypeBtn1: MaterialButton = view.findViewById(R.id.typeBtn1)
        val videoTypeBtn2: MaterialButton = view.findViewById(R.id.typeBtn2)
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
                putExtra(VideoInfoActivity.VIDEO_ACTOR, video.actor)
                putExtra(VideoInfoActivity.VIDEO_ACTOR_INFO, video.actorInfo)
                putExtra(VideoInfoActivity.VIDEO_ID, video.video_id.toString())
                putExtra(VideoInfoActivity.VIDEO_LABEL, video.label)
                putExtra(VideoInfoActivity.VIDEO_PUBLISH_TIME, video.publish_time)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        holder.videoTypeBtn0.text = "想看"
        holder.videoTypeBtn1.text = "在看"
        holder.videoTypeBtn2.text = "看过"


        for (i in markList.indices) {
            if (markList[i].video_id == video.video_id) {
                when (markList[i].type) {
                    0 -> holder.videoTypeBtn0.text = "已想看"
                    1 -> holder.videoTypeBtn1.text = "已在看"
                    2 -> holder.videoTypeBtn2.text = "已看过"
                }
                break
            }
        }

        if (video.video_name.length > 9) {
            val shortTitle = video.video_name.substring(0, 9) + " ..."
            holder.videoName.text = shortTitle
        } else
            holder.videoName.text = video.video_name

        if (video.info.length > 60) {
            val shortInfo = video.info.substring(0, 60) + " ..."
            holder.videoInfo.text = shortInfo
        } else
            holder.videoInfo.text = video.info
        holder.videoActor.text = video.actor

        Glide.with(context).load(video.pic).into(holder.videoImage)

        val userId = BVMApplication.USER?.user_id ?: 0

        // 设置标记按钮监听事件
        holder.videoTypeBtn0.setOnClickListener {
//            val position = holder.adapterPosition
            val video = videoList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertVideoMark(VideoMark(userId, video.video_id ?: 0, 0, dateFormat.format(date)))
            if (holder.videoTypeBtn0.text == "已想看")
                holder.videoTypeBtn0.text = "想看"
            else
                holder.videoTypeBtn0.text = "已想看"
            holder.videoTypeBtn1.text = "在看"
            holder.videoTypeBtn2.text = "看过"
        }
        holder.videoTypeBtn1.setOnClickListener {
//            val position = holder.adapterPosition
            val video = videoList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertVideoMark(VideoMark(userId, video.video_id ?: 0, 1, dateFormat.format(date)))
            holder.videoTypeBtn0.text = "想看"
            if (holder.videoTypeBtn1.text == "已在看")
                holder.videoTypeBtn1.text = "在看"
            else
                holder.videoTypeBtn1.text = "已在看"
            holder.videoTypeBtn2.text = "看过"
        }
        holder.videoTypeBtn2.setOnClickListener {
//            val position = holder.adapterPosition
            val video = videoList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertVideoMark(VideoMark(userId, video.video_id ?: 0, 2, dateFormat.format(date)))
            holder.videoTypeBtn0.text = "想看"
            holder.videoTypeBtn1.text = "在看"
            if (holder.videoTypeBtn2.text == "已看过")
                holder.videoTypeBtn2.text = "看过"
            else
                holder.videoTypeBtn2.text = "已看过"
        }



        holder.deleteBtn.setOnClickListener {
            viewModel.deleteVideoById(video.video_id ?: 0)

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
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
                notifyDataSetChanged()
            }
        }
    }
}
