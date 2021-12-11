package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.BookDao
import com.example.bvm.logic.dao.MusicDao
import com.example.bvm.logic.dao.VideoDao
import com.example.bvm.logic.model.Book
import com.example.bvm.logic.model.Music
import com.example.bvm.logic.model.Video

@Database(version = 1, entities = [Book::class, Video::class, Music::class])
abstract class ModelDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun videoDao(): VideoDao

    abstract fun musicDao(): MusicDao

    companion object {

        private var instance: ModelDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): ModelDatabase {
            instance?.let { // 存在instance实例则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                ModelDatabase::class.java, "model_database")
                .build().apply {
                    instance = this
                }
        }
    }
}