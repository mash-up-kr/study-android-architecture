package com.namget.myarchitecture.data.source.local

import android.content.Context
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Namget on 2019.10.24.
 */
object RepoLocalDataSourceImpl : RepoLocalDataSource {
    private lateinit var appDatabase: AppDatabase

    fun provideAppDatabase(context: Context) {
        appDatabase = AppDatabase.getInstance(context)
    }

    override fun insertRepoData(repoItem: RepoItemEntity): Completable =
        appDatabase.repoDao().insert(repoItem)

    override fun selectRepoData(): Observable<List<RepoItemEntity>> =
        appDatabase.repoDao().selectRepoList()

}