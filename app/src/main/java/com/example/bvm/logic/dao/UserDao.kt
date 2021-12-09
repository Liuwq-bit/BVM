package com.example.bvm.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bvm.logic.model.User


@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Long

    @Query("select * from User")
    fun loadAllUsers(): List<User>

    @Query("select * from User where user_name = :user_name")
    fun searchUserByName(user_name: String): List<User>

    @Query("select * from User where user_id = :user_id")
    fun searchUserById(user_id: Long): List<User>
}