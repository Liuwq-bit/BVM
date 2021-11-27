package com.example.bvm.logic.book.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.book.dao.BookDao
import com.example.bvm.logic.book.model.Book

@Database(version = 1, entities = [Book::class])
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {

        private var instance: BookDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): BookDatabase {
            instance?.let { // 存在instance实例则直接返回，否则创建
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                BookDatabase::class.java, "book_database")
                .build().apply {
                    instance = this
                }
        }
    }
}