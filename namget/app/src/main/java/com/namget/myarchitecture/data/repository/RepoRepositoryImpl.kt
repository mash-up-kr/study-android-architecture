package com.namget.myarchitecture.data.repository

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import com.namget.myarchitecture.data.source.local.RepoLocalDataSource
import com.namget.myarchitecture.data.source.local.RepoLocalDataSourceImpl
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.data.source.remote.RepoRemoteDataSource
import com.namget.myarchitecture.data.source.remote.RepoRemoteDataSourceImpl
import com.namget.myarchitecture.data.source.remote.RetrofitBuilder
import com.namget.myarchitecture.ext.withScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Created by Namget on 2019.10.25.
 */
object RepoRepositoryImpl : RepoRepository {
    private val repoRemoteDataSource: RepoRemoteDataSource by lazy {
        RepoRemoteDataSourceImpl
    }
    private val repoLocalDataSource: RepoLocalDataSource by lazy {
        RepoLocalDataSourceImpl
    }

    override fun getRepositoryList(searchName: String): Single<RepoListResponse> =
        repoRemoteDataSource.getRepositoryList(searchName)
            .withScheduler()

    override fun getProfileInfo(
        userUrl: String,
        repoUrl: String
    ): Single<Pair<UserInfoResponse, RepoInfoResponse>> =
        Single.zip(
            repoRemoteDataSource.getUserInfo(userUrl),
            RetrofitBuilder.createApiService()
                .getRepoInfo(repoUrl),
            BiFunction<UserInfoResponse, RepoInfoResponse, Pair<UserInfoResponse, RepoInfoResponse>> { user, repo ->
                Pair(user, repo)
            }).withScheduler()


    override fun insertRepoData(repoItem: RepoItemEntity): Completable =
        repoLocalDataSource.insertRepoData(repoItem)
            .withScheduler()

    override fun selectRepoData(): Observable<List<RepoItemEntity>> =
        repoLocalDataSource.selectRepoData()
            .withScheduler()
}