package com.example.bvm.ui.music.Adapter

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
import com.example.bvm.logic.model.Music
import com.example.bvm.ui.music.MusicInfoActivity

class MusicAdapter(private val fragment: Fragment, private val musicList: List<Music>):
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicName: TextView = view.findViewById(R.id.itemTitle)
        val musicInfo: TextView = view.findViewById(R.id.itemInfo)
        val musicImage: ImageView = view.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )

        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val music = musicList[position]
            val intent = Intent(context, MusicInfoActivity::class.java).apply {
                putExtra(MusicInfoActivity.MUSIC_TITLE, music.music_name)
                putExtra(MusicInfoActivity.MUSIC_INFO, music.info)
                putExtra(MusicInfoActivity.MUSIC_IMAGE, music.pic)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK   // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        if (music.music_name.length > 9) {
            val shortTitle = music.music_name.substring(0, 9) + " ..."
            holder.musicName.text = shortTitle
        } else
            holder.musicName.text = music.music_name

        if (music.info.length > 30) {
            val shortInfo = music.info.substring(0, 29) + " ..."
            holder.musicInfo.text = shortInfo
        } else
            holder.musicInfo.text = music.info

        Glide.with(context).load(music.pic).into(holder.musicImage)
    }

    override fun getItemCount() = musicList.size
}