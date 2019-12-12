package com.example.myapplication.network

import com.example.myapplication.model.GithubApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiProvider {
    fun getService(): GithubApi = retrofit.create(
        GithubApi::class.java)

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(" https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}