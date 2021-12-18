package com.example.bvm.ui.music.Adapter

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
import com.example.bvm.logic.model.MusicMark
import com.example.bvm.logic.model.Music
import com.example.bvm.ui.book.Adapter.BookAdapter
import com.example.bvm.ui.music.MusicInfoActivity
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.book_list.*
import java.util.*
import kotlin.concurrent.thread

class MusicAdapter(private val fragment: Fragment, private val musicList: List<Music>, private val markList: List<MusicMark>):
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    val viewModel by lazy { ViewModelProviders.of(fragment).get(MusicViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicName: TextView = view.findViewById(R.id.itemTitle)
        val musicInfo: TextView = view.findViewById(R.id.itemInfo)
        val musicSinger: TextView = view.findViewById(R.id.authorText)
        val musicImage: ImageView = view.findViewById(R.id.itemImage)
        val deleteBtn: MaterialButton = view.findViewById(R.id.deleteItemBtn)
        val musicTypeBtn0: MaterialButton = view.findViewById(R.id.typeBtn0)
        val musicTypeBtn1: MaterialButton = view.findViewById(R.id.typeBtn1)
        val musicTypeBtn2: MaterialButton = view.findViewById(R.id.typeBtn2)
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
            val music = musicList[position]
            val intent = Intent(context, MusicInfoActivity::class.java).apply {
                putExtra(MusicInfoActivity.MUSIC_TITLE, music.music_name)
                putExtra(MusicInfoActivity.MUSIC_INFO, music.info)
                putExtra(MusicInfoActivity.MUSIC_IMAGE, music.pic)
                putExtra(MusicInfoActivity.MUSIC_SINGER, music.singer)
                putExtra(MusicInfoActivity.MUSIC_SINGER_INFO, music.singerInfo)
                putExtra(MusicInfoActivity.MUSIC_ID, music.music_id.toString())
                flags = Intent.FLAG_ACTIVITY_NEW_TASK   // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        holder.musicTypeBtn0.text = "想听"
        holder.musicTypeBtn1.text = "在听"
        holder.musicTypeBtn2.text = "听过"

        holder.musicName.text = music.music_name

        for (i in markList.indices) {
            if (markList[i].music_id == music.music_id) {
                when (markList[i].type) {
                    0 -> holder.musicTypeBtn0.text = "已想听"
                    1 -> holder.musicTypeBtn1.text = "已在听"
                    2 -> holder.musicTypeBtn2.text = "已听过"
                }
                break
            }
        }

        if (music.info.length > 60) {
            val info = music.info.substring(0, 60) + "..."
            holder.musicInfo.text = info
        } else
            holder.musicInfo.text = music.info

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

        val userId = BVMApplication.USER?.user_id ?: 0

        // 设置标记按钮监听事件
        holder.musicTypeBtn0.setOnClickListener {
//            val position = holder.adapterPosition
            val music = musicList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertMusicMark(MusicMark(userId, music.music_id, 0, dateFormat.format(date)))
            holder.musicTypeBtn0.text = "已想听"
            holder.musicTypeBtn1.text = "在听"
            holder.musicTypeBtn2.text = "听过"
        }
        holder.musicTypeBtn1.setOnClickListener {
//            val position = holder.adapterPosition
            val music = musicList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertMusicMark(MusicMark(userId, music.music_id, 1, dateFormat.format(date)))
            holder.musicTypeBtn0.text = "想听"
            holder.musicTypeBtn1.text = "已在听"
            holder.musicTypeBtn2.text = "听过"
        }
        holder.musicTypeBtn2.setOnClickListener {
//            val position = holder.adapterPosition
            val music = musicList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertMusicMark(MusicMark(userId, music.music_id, 2, dateFormat.format(date)))
            holder.musicTypeBtn0.text = "想听"
            holder.musicTypeBtn1.text = "在听"
            holder.musicTypeBtn2.text = "已听过"
        }



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
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
                notifyDataSetChanged()
            }
        }
    }

}