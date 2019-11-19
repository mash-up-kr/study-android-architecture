package com.tistory.blackjin.domain.di

import com.tistory.blackjin.domain.interactor.usecases.*
import org.koin.dsl.module

val usecaseModule = module {

    factory {
        AddRepoHistoryUsecase(get(), get())
    }

    factory {
        ClearRepoHistoryUsecase(get(), get())
    }

    factory {
        GetRepoHistoryUsecase(get(), get())
    }

    factory {
        GetReposUsecase(get(), get())
    }

    factory {
        GetRepoUsecase(get(), get(), get())
    }
}