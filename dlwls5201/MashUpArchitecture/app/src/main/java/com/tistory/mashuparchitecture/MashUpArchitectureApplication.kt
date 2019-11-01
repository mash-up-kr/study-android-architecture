package com.tistory.mashuparchitecture

import android.app.Application
import com.tistory.blackjin.data.di.networkModule
import com.tistory.blackjin.data.di.repositoryModule
import com.tistory.blackjin.domain.di.usecaseModule
import com.tistory.mashuparchitecture.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import timber.log.Timber

class MashUpArchitectureApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            logger(
                if (BuildConfig.DEBUG) {
                    AndroidLogger()
                } else {
                    EmptyLogger()
                }
            )

            androidContext(this@MashUpArchitectureApplication)

            modules(
                listOf(
                    appModule,
                    networkModule, repositoryModule,
                    usecaseModule
                )
            )
        }
    }
}