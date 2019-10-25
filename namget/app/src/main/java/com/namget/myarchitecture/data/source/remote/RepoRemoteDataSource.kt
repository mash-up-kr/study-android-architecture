package com.namget.myarchitecture.data.source.remote

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import io.reactivex.Single

/**
 * Created by Namget on 2019.10.24.
 */
interface RepoRemoteDataSource{
    fun getRepositoryList(searchName: String): Single<RepoListResponse>
    fun getUserInfo(id: String): Single<UserInfoResponse>
    fun getRepoInfo(repoUrl: String): Single<RepoInfoResponse>
}