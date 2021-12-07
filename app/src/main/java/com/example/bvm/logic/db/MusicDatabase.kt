package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.MusicDao
import com.example.bvm.logic.model.Music


@Database(version = 1, entities = [Music::class])
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    companion object {

        private var instance: MusicDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MusicDatabase {
            instance?.let { // 存在instance实例则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                MusicDatabase::class.java, "music_database")
                .build().apply {
                    instance = this
                }
        }
    }
}