package com.example.mashuparchitecture.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper private constructor() {
    private val retrofit by lazy {
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    val apiService by lazy { retrofit.create(GithubApiService::class.java) }

    companion object {
        const val API_URL = "https://api.github.com/"
        private var INSTANCE: RetrofitHelper? = null

        fun getInstance() = INSTANCE ?: RetrofitHelper().apply { INSTANCE = this }
    }
}