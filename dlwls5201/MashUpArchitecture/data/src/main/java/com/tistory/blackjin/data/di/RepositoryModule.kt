package com.tistory.blackjin.data.di

import com.tistory.blackjin.data.repositoryimpl.RepoRepositoryImpl
import com.tistory.blackjin.data.repositoryimpl.UserRepositoryImpl
import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    // repository
    single<RepoRepository> {
        RepoRepositoryImpl(get(), get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }


}