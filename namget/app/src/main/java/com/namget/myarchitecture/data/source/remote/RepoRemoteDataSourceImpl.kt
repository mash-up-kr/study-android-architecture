package com.namget.myarchitecture.data.source.remote

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import io.reactivex.Single

/**
 * Created by Namget on 2019.10.24.
 */
object RepoRemoteDataSourceImpl : RepoRemoteDataSource {
    private val apiService: ApiService = RetrofitBuilder.repoApi

    override fun getRepositoryList(searchName: String): Single<RepoListResponse> =
        apiService.getRepositoryList(searchName)


    override fun getUserInfo(id: String): Single<UserInfoResponse> =
        apiService.getUserInfo(id)


    override fun getRepoInfo(repoUrl: String): Single<RepoInfoResponse> =
        apiService.getRepoInfo(repoUrl)
}