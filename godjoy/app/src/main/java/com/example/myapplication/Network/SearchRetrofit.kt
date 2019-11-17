package com.example.myapplication.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchRetrofit {
    fun getService(): RetrofitService = retrofit.create(RetrofitService::class.java)

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(" https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}