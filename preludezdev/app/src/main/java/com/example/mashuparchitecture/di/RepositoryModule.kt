package com.example.mashuparchitecture.di

import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.RepositoryImpl
import com.example.mashuparchitecture.data.source.local.LocalDataSource
import com.example.mashuparchitecture.data.source.local.LocalDataSourceImpl
import com.example.mashuparchitecture.data.source.remote.RemoteDataSource
import com.example.mashuparchitecture.data.source.remote.RemoteDataSourceImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { RepositoryImpl(get(), get()) as Repository }
    single { RemoteDataSourceImpl(get()) as RemoteDataSource }
    single { LocalDataSourceImpl() as LocalDataSource }
}