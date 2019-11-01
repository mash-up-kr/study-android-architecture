package com.tistory.blackjin.data.di

import com.tistory.blackjin.data.repositoryimpl.RepoRepositoryImpl
import com.tistory.blackjin.data.source.remote.RepoApi
import com.tistory.blackjin.data.source.remote.UserApi
import com.tistory.blackjin.domain.repository.RepoRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {

    // repository
    single<RepoRepository> {
        RepoRepositoryImpl(get())
    }

    // local

    // remote
    single { get<Retrofit>().create(RepoApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }
}