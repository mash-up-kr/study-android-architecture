package com.example.mashuparchitecture

import android.app.Application
import com.example.mashuparchitecture.di.networkModule
import com.example.mashuparchitecture.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubApplication : Application() {

    private val moduleList = listOf(repositoryModule, networkModule)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubApplication)

            modules(moduleList)
        }
    }
}