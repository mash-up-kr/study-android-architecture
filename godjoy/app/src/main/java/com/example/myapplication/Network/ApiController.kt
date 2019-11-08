package com.example.myapplication.Network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiController : Application(){
    private val baseURL = " https://api.github.com/"
    lateinit var networkService : NetworkService
    lateinit var retrofit: Retrofit

    companion object{
        lateinit var instance : ApiController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    fun buildNetwork(){
        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        networkService = retrofit.create(NetworkService::class.java)
    }
}