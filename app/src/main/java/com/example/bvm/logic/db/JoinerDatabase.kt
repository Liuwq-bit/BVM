package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.ActorDao
import com.example.bvm.logic.dao.AuthorDao
import com.example.bvm.logic.dao.SingerDao
import com.example.bvm.logic.model.Actor
import com.example.bvm.logic.model.Author
import com.example.bvm.logic.model.Singer

@Database(version = 1, entities = [Author::class, Actor::class, Singer::class])
abstract class JoinerDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao

    abstract fun actorDao(): ActorDao

    abstract fun singerDao(): SingerDao

    companion object {

        private var instance: JoinerDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): JoinerDatabase {
            instance?.let { // 存在instance实例则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                JoinerDatabase::class.java, "joiner_database")
                .build().apply {
                    instance = this
                }
        }
    }
}