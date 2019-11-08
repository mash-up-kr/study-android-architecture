package com.tistory.blackjin.data.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    val baseUrl = "https://api.github.com/"

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.createAsync()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {

            //TimeOut 시간을 지정합니다.
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)

            // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(get())
        }.build()
    }

    single<okhttp3.Interceptor> {

        val debug: Boolean by inject(named("debug"))

        HttpLoggingInterceptor().apply {
            level = if (debug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

}