package com.example.bvm.ui.music.Adapter

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
import com.example.bvm.logic.model.Music
import com.example.bvm.ui.book.Adapter.BookAdapter
import com.example.bvm.ui.music.MusicInfoActivity
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.book_list.*
import kotlin.concurrent.thread

class MusicAdapter(private val fragment: Fragment, private val musicList: List<Music>):
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    val viewModel by lazy { ViewModelProviders.of(fragment).get(MusicViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicName: TextView = view.findViewById(R.id.itemTitle)
        val musicInfo: TextView = view.findViewById(R.id.itemInfo)
        val musicSinger: TextView = view.findViewById(R.id.authorText)
        val musicImage: ImageView = view.findViewById(R.id.itemImage)
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
            val music = musicList[position]
            val intent = Intent(context, MusicInfoActivity::class.java).apply {
                putExtra(MusicInfoActivity.MUSIC_TITLE, music.music_name)
                putExtra(MusicInfoActivity.MUSIC_INFO, music.info)
                putExtra(MusicInfoActivity.MUSIC_IMAGE, music.pic)
                putExtra(MusicInfoActivity.MUSIC_SINGER, music.singer)
                putExtra(MusicInfoActivity.MUSIC_SINGER_INFO, music.singerInfo)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK   // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        holder.musicName.text = music.music_name

        val info = music.info.substring(0, 100)
        holder.musicInfo.text = info

//        val re = Regex("^[a-zA-Z]*")  // 使用正则表达式匹配数据
//        if (music.music_name.length > 9) {
//            val shortTitle = music.music_name.substring(0, 9) + " ..."
//            holder.musicName.text = shortTitle
//        } else
//            holder.musicName.text = music.music_name
//
//        if (music.info.length > 30) {
//            val shortInfo = music.info.substring(0, 29) + " ..."
//            holder.musicInfo.text = shortInfo
//        } else
//            holder.musicInfo.text = music.info

        holder.musicSinger.text = music.singer
        Glide.with(context).load(music.pic).into(holder.musicImage)

        holder.deleteBtn.setOnClickListener {
            viewModel.deleteMusicById(music.music_id)

            viewModel.musicLiveData.observe(fragment.viewLifecycleOwner, Observer { result -> // 动态查询数据
                val musics = result.getOrNull()
                if (musics != null) {
                    viewModel.musicList.clear()
                    viewModel.musicList.addAll(musics)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "未能查询到任何书籍", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            })

            reFreshMusic()
        }

    }

    override fun getItemCount() = musicList.size

    private fun reFreshMusic() {
        thread {
            Thread.sleep(500)
            fragment.activity?.runOnUiThread {

                viewModel.searchAllMusics()  // 显示所有音乐

                notifyDataSetChanged()
            }
        }
    }

}