package com.namget.myarchitecture.data.source.remote

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
object RepoRemoteDataSourceImpl : RepoDataSource {
    private val apiService: ApiService = RetrofitBuilder.repoApi

    override fun insertRepoData(repoItem: RepoItemEntity): Completable {
        throw UnsupportedOperationException()
    }
    override fun selectRepoData(): Observable<List<RepoItemEntity>> {
        throw UnsupportedOperationException()
    }

    override fun getRepositoryList(searchName: String): Single<RepoListResponse> =
        apiService.getRepositoryList(searchName)


    override fun getUserInfo(id: String): Single<UserInfoResponse> =
        apiService.getUserInfo(id)


    override fun getRepoInfo(repoUrl: String): Single<RepoInfoResponse> =
        apiService.getRepoInfo(repoUrl)
}