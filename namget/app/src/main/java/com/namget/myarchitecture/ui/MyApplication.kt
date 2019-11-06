package com.namget.myarchitecture.ui

import android.app.Application
import com.namget.myarchitecture.data.source.local.RepoLocalDataSourceImpl

/**
 * Created by Namget on 2019.10.25.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRepoLocalRepository()
    }

    private fun initRepoLocalRepository() {
        RepoLocalDataSourceImpl.provideAppDatabase(applicationContext)
    }
}