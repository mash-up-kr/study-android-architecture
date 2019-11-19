package com.tistory.blackjin.data.di

import com.tistory.blackjin.data.source.remote.RepoApi
import com.tistory.blackjin.data.source.remote.UserApi
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {

    single {
        get<Retrofit>().create(RepoApi::class.java)
    }

    single {
        get<Retrofit>().create(UserApi::class.java)
    }
}