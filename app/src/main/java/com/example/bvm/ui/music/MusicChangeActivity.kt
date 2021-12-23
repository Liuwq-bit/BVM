package com.example.bvm.ui.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.logic.model.Music
import com.example.bvm.logic.model.Singer
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import kotlinx.android.synthetic.main.activity_music_change.*
import java.text.SimpleDateFormat
import java.util.*

class MusicChangeActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_change)
        setSupportActionBar(changeMusicToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        val musicId = intent.getStringExtra(MusicInfoActivity.MUSIC_ID) ?: ""
        val musicTitle = intent.getStringExtra(MusicInfoActivity.MUSIC_TITLE) ?: ""
        val musicInfo = intent.getStringExtra(MusicInfoActivity.MUSIC_INFO) ?: ""
        val musicPic = intent.getStringExtra(MusicInfoActivity.MUSIC_IMAGE) ?: ""
        val musicSinger = intent.getStringExtra(MusicInfoActivity.MUSIC_SINGER) ?: ""
        val musicSingerInfo = intent.getStringExtra(MusicInfoActivity.MUSIC_SINGER_INFO) ?: ""
        val musicLabel = intent.getStringExtra(MusicInfoActivity.MUSIC_LABEL) ?: ""
        val musicPublishTime = intent.getStringExtra(MusicInfoActivity.MUSIC_PUBLISH_TIME) ?: ""

        changeMusicNameText.editText?.setText(musicTitle)
        changeMusicSingerText.editText?.setText(musicSinger)
        changeMusicSingerInfoText.editText?.setText(musicSingerInfo)
        changeMusicLabelText.editText?.setText(musicLabel)
        changeMusicInfoText.editText?.setText(musicInfo)
        changeMusicPublishTimeText.editText?.setText(musicPublishTime)
        changeMusicPicText.editText?.setText(musicPic)

        changeMusicCommitFab.setOnClickListener {
            // todo 增加格式判断机制
            val musicName = changeMusicNameText.editText?.text.toString()
            val musicSinger = changeMusicSingerText.editText?.text.toString()
            val musicSingerInfo = changeMusicSingerInfoText.editText?.text.toString()
            val musicLabel = changeMusicLabelText.editText?.text.toString()
            val musicInfo = changeMusicInfoText.editText?.text.toString()
            val musicPublish = changeMusicPublishTimeText.editText?.text.toString()
            val musicPic = changeMusicPicText.editText?.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            val music = Music(musicId.toLong(), musicName, musicLabel, musicInfo, musicSinger, musicSingerInfo, dateFormat.format(date), musicPublish, musicPic)
            val singer = Singer(musicSinger, musicSingerInfo)
            viewModel.insertMusics(music, singer)

            Toast.makeText(BVMApplication.context, "修改成功", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}