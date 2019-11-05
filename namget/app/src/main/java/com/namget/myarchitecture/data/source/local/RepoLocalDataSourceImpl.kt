package com.namget.myarchitecture.data.source.local

import android.content.Context
import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import com.namget.myarchitecture.data.source.RepoDataSource
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Namget on 2019.10.24.
 */
object RepoLocalDataSourceImpl : RepoDataSource {
    private lateinit var appDatabase: AppDatabase

    override fun getRepositoryList(searchName: String): Single<RepoListResponse> {
        throw UnsupportedOperationException()
    }
    override fun getUserInfo(id: String): Single<UserInfoResponse> {
        throw UnsupportedOperationException()
    }
    override fun getRepoInfo(repoUrl: String): Single<RepoInfoResponse> {
        throw UnsupportedOperationException()
    }

    fun provideAppDatabase(context: Context) {
        appDatabase = AppDatabase.getInstance(context)
    }

    override fun insertRepoData(repoItem: RepoItemEntity): Completable =
        appDatabase.repoDao().insert(repoItem)

    override fun selectRepoData(): Observable<List<RepoItemEntity>> =
        appDatabase.repoDao().selectRepoList()

}