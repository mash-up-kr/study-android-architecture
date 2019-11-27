package com.runeanim.mytoyproject.di

import androidx.room.Room
import com.runeanim.mytoyproject.data.source.DefaultRepositoriesRepository
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.local.GithubDatabase
import com.runeanim.mytoyproject.data.source.local.RepositoriesLocalDataSource
import com.runeanim.mytoyproject.data.source.remote.RepositoriesRemoteDataSource
import com.runeanim.mytoyproject.ui.detail.DetailContract
import com.runeanim.mytoyproject.ui.detail.DetailPresenter
import com.runeanim.mytoyproject.ui.main.MainContract
import com.runeanim.mytoyproject.ui.main.MainPresenter
import com.runeanim.mytoyproject.ui.search.SearchContract
import com.runeanim.mytoyproject.ui.search.SearchPresenter
import kotlinx.coroutines.CoroutineScope
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

    factory { (repoUrl: String, userId: String, view: DetailContract.View) ->
        DetailPresenter(
            repoUrl,
            userId,
            get(),
            get(),
            view
        )
    }
    factory { (view: MainContract.View) ->
        MainPresenter(
            get(),
            get(),
            view
        )
    }
    factory { (view: SearchContract.View) ->
        SearchPresenter(
            get(),
            get(),
            view
        )
    }
}
