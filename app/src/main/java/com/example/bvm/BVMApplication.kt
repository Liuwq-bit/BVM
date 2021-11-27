package com.example.bvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*
    全局Context
 */
class BVMApplication : Application() {

    companion object {
        const val TOKEN = "令牌"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}