package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.*
import com.example.bvm.logic.model.*

@Database(version = 1, entities = [User::class, Book::class, Video::class, Music::class,
                                    BookMark::class, VideoMark::class, MusicMark::class,
                                    BookComment::class, VideoComment::class, MusicComment::class])
abstract class BVMDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun videoDao(): VideoDao
    abstract fun musicDao(): MusicDao
    abstract fun bookMarkDao(): BookMarkDao
    abstract fun videoMarkDao(): VideoMarkDao
    abstract fun musicMarkDao(): MusicMarkDao
    abstract fun bookCommentDao(): BookCommentDao
    abstract fun videoCommentDao(): VideoCommentDao
    abstract fun musicCommentDao(): MusicCommentDao

    companion object {
        private var instance: BVMDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): BVMDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                BVMDatabase::class.java, "bvm_database")
                .build().apply {
                    instance = this
                }
        }
    }
}