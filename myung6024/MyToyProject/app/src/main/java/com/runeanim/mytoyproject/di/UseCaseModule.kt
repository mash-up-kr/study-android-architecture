package com.runeanim.mytoyproject.di

import com.runeanim.mytoyproject.domain.*
import org.koin.dsl.module

val UseCaseModule = module {
    single { SearchRepositoriesUseCase(get()) }
    single { GetRepositoryInfoUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
    single { GetRepositoriesUseCase(get()) }
    single { SaveRepositoryUseCase(get()) }
    single { RemoveAllRepositoriesUseCase(get()) }
}