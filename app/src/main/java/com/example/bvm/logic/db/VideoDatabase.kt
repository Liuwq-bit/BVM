package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.VideoDao
import com.example.bvm.logic.model.Video


@Database(version = 1, entities = [Video::class])
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {

        private var instance: VideoDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): VideoDatabase {
            instance?.let { // 存在instance实例则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                VideoDatabase::class.java, "video_database")
                .build().apply {
                    instance = this
                }
        }
    }
}