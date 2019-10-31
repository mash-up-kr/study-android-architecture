package com.runeanim.mytoyproject.di

import androidx.room.Room
import com.runeanim.mytoyproject.data.source.DefaultRepositoriesRepository
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.local.GithubDatabase
import com.runeanim.mytoyproject.data.source.local.RepositoriesLocalDataSource
import com.runeanim.mytoyproject.data.source.remote.RepositoriesRemoteDataSource
import com.runeanim.mytoyproject.domain.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ApplicationModule = module {
    single { Dispatchers.IO }
    single {
        Room.databaseBuilder(
            androidContext(),
            GithubDatabase::class.java,
            "Repositories.db"
        ).build().repositoriesDao()
    }

    single { RepositoriesRemoteDataSource(get(), get()) }
    single { RepositoriesLocalDataSource(get(), get()) }
    single { DefaultRepositoriesRepository(get(), get()) as RepositoriesRepository }
    single { SearchRepositoriesUseCase(get()) }
    single { GetRepositoryInfoUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
    single { GetRepositoriesUseCase(get()) }
    single { SaveRepositoryUseCase(get()) }
}
