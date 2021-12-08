package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.AuthorDao
import com.example.bvm.logic.model.Author

@Database(version = 1, entities = [Author::class])
abstract class AuthorDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao

    companion object {

        private var instance: AuthorDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AuthorDatabase {
            instance?.let { // 存在instance实例则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AuthorDatabase::class.java, "author_database")
                .build().apply {
                    instance = this
                }
        }
    }
}