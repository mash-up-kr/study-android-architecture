package com.example.mashuparchitecture.di

import com.example.mashuparchitecture.network.GithubApiService
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_URL = "https://api.github.com/"

val networkModule = module {
    single {
        Retrofit
            .Builder()
            .addConverterFactory(get())
            .baseUrl(API_URL)
            .build()
    }

    single { GsonConverterFactory.create() as Converter.Factory }

    single { (get() as Retrofit).create(GithubApiService::class.java) }


}