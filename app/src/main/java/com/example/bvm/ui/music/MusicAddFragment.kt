package com.example.bvm.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.R
import com.example.bvm.logic.model.Music
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import kotlinx.android.synthetic.main.music_add.*
import java.text.SimpleDateFormat
import java.util.*

class MusicAddFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.music_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        musicCommitFab.setOnClickListener {
            // todo 增加格式判断机制
            val musicName = musicNameText.editText?.text.toString()
            val musicSinger = musicSingerText.editText?.text.toString()
            val musicLabel = musicLabelText.editText?.text.toString()
            val musicInfo = musicInfoText.editText?.text.toString()
            val musicPublish = musicPublishTimeText.editText?.text.toString()
            val musicPic = musicPicText.editText?.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            val music = Music(musicName, musicLabel, musicInfo, dateFormat.format(date), musicPublish, musicPic)
            viewModel.insertMusics(music)

            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
        }
    }
}