package com.example.bvm.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bvm.logic.Repository
import com.example.bvm.logic.model.User
import kotlin.concurrent.thread

/**
 * 登录与注册页面的ViewModel
 */
class CommonViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val userList = ArrayList<User>()

    val userLiveData = Transformations.switchMap(searchLiveData) { user_name ->
        Repository.searchUserByName(user_name)
    }

    // 用户注册录入信息
    fun insertUser(user: User) {
        thread {
            Repository.insertUser(user)
        }
    }

    fun searchUserByName(user_name: String) {
        searchLiveData.value = user_name
    }


}