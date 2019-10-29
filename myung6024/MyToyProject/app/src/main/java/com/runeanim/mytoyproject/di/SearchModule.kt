package com.runeanim.mytoyproject.di

import com.runeanim.mytoyproject.data.source.DefaultRepositoriesRepository
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.remote.RepositoriesRemoteDataSource
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val SearchModule = module {
    single { Dispatchers.IO }
    single { RepositoriesRemoteDataSource(get(), get()) }
    single { DefaultRepositoriesRepository(get(), get()) as RepositoriesRepository }
    single { SearchRepositoriesUseCase(get()) }
    single { GetRepositoryInfoUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
}
