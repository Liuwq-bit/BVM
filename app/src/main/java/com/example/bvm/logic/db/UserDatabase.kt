package com.example.bvm.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bvm.logic.dao.UserDao
import com.example.bvm.logic.model.User

@Database(version = 1, entities = [User::class])
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var instance: UserDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): UserDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java, "user_database")
                .build().apply {
                    instance = this
                }
        }
    }
}