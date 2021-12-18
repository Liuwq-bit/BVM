package com.example.bvm.ui.music

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.R
import com.example.bvm.logic.model.MusicComment
import com.example.bvm.ui.music.ViewModel.MusicViewModel
import kotlinx.android.synthetic.main.activity_music_comment.*
import java.util.*

class MusicCommentActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MusicViewModel::class.java) }
    var rating: Float = 2.5F

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_comment)
        setSupportActionBar(musicCommentToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
        val musicId = intent.getStringExtra(MusicInfoActivity.MUSIC_ID) ?: "0"
        val userId = BVMApplication.USER?.user_id ?: 0

        musicRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
//            Toast.makeText(this, "rating: $rating", Toast.LENGTH_SHORT).show()
            this.rating = rating
        }

        musicRatingCommentFab.setOnClickListener {
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val comment = musicCommentText.editText?.text.toString()
            viewModel.insertMusicComment(MusicComment(musicId.toLong(), userId, comment, rating, dateFormat.format(date)))
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