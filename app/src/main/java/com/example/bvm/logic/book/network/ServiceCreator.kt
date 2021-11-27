package com.example.bvm.logic.book.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    图书搜索的Retrofit构建器
 */

object ServiceCreator {

//    private const val BASE_URL = "https://api.feelyou.top/"
    private const val BASE_URL = "https://api.wmdb.tv/api/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}