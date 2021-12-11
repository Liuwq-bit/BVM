package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.ActorOfVideoDao
import com.example.bvm.logic.dao.AuthorOfBookDao
import com.example.bvm.logic.dao.SingerOfMusicDao
import com.example.bvm.logic.model.ActorOfVideo
import com.example.bvm.logic.model.AuthorOfBook
import com.example.bvm.logic.model.SingerOfMusic

@Database(version = 1, entities = [AuthorOfBook::class, ActorOfVideo::class, SingerOfMusic::class])
abstract class LinkDatabase : RoomDatabase() {

    abstract fun authorOfBookDao(): AuthorOfBookDao

    abstract fun actorOfVideoDao(): ActorOfVideoDao

    abstract fun singerOfMusic(): SingerOfMusicDao

    companion object {

        private var instance: LinkDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): LinkDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                LinkDatabase::class.java, "link_database")
                .build().apply {
                    instance = this
                }
        }
    }
}