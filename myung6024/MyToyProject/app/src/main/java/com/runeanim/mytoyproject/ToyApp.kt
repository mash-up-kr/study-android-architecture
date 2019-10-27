package com.runeanim.mytoyproject

import android.app.Application
import com.runeanim.mytoyproject.di.ApiModule
import com.runeanim.mytoyproject.di.NetworkModule
import com.runeanim.mytoyproject.di.SearchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ToyApp)
            modules(
                listOf(
                    NetworkModule,
                    ApiModule,
                    SearchModule
                )
            )
        }
    }
}
