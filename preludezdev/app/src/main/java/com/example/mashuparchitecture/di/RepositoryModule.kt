package com.example.mashuparchitecture.di

import androidx.room.Room
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.RepositoryImpl
import com.example.mashuparchitecture.data.source.local.LocalDataSource
import com.example.mashuparchitecture.data.source.local.LocalDataSourceImpl
import com.example.mashuparchitecture.data.source.local.LocalDatabase
import com.example.mashuparchitecture.data.source.remote.RemoteDataSource
import com.example.mashuparchitecture.data.source.remote.RemoteDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { RepositoryImpl(get(named("Remote")), get(named("Local"))) as Repository }
    single(named("Remote")) { RemoteDataSourceImpl(get()) as RemoteDataSource }
    single(named("Local")) { LocalDataSourceImpl(get(named("Dao"))) as LocalDataSource }

    single(named("Dao")) { (get() as LocalDatabase).getGithubRepoDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            "github.db"
        ).build()
    }
}