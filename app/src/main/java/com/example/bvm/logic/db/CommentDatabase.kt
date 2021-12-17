package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.BookCommentDao
import com.example.bvm.logic.dao.MusicCommentDao
import com.example.bvm.logic.dao.VideoCommentDao
import com.example.bvm.logic.model.BookComment
import com.example.bvm.logic.model.MusicComment
import com.example.bvm.logic.model.VideoComment

@Database(version = 1, entities = [BookComment::class, VideoComment::class, MusicComment::class])
abstract class CommentDatabase : RoomDatabase() {

    abstract fun bookCommentDao(): BookCommentDao

    abstract fun videoCommentDao(): VideoCommentDao

    abstract fun musicCommentDao(): MusicCommentDao

    companion object {

        private var instance: CommentDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): CommentDatabase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(context.applicationContext,
                CommentDatabase::class.java, "comment_database")
                .build().apply {
                    instance = this
                }
        }
    }
}