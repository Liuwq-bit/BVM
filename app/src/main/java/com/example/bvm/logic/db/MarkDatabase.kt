package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.BookMarkDao
import com.example.bvm.logic.dao.MusicMarkDao
import com.example.bvm.logic.dao.VideoMarkDao
import com.example.bvm.logic.model.BookMark
import com.example.bvm.logic.model.MusicMark
import com.example.bvm.logic.model.VideoMark

@Database(version = 1, entities = [BookMark::class, VideoMark::class, MusicMark::class])
abstract class MarkDatabase : RoomDatabase() {

    abstract fun bookMarkDao(): BookMarkDao

    abstract fun videoMarkDao(): VideoMarkDao

    abstract fun musicMarkDao(): MusicMarkDao

    companion object {

        private var instance: MarkDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MarkDatabase {
            instance?.let { // 存在instance则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                MarkDatabase::class.java, "mark_database")
                .build().apply {
                    instance = this
                }
        }
    }
}