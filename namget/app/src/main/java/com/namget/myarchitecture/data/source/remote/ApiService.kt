package com.namget.myarchitecture.data.source.remote

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Namget on 2019.10.22.
 */
interface ApiService {

    @GET("search/repositories")
    fun getRepositoryList(@Query("q") searchName: String): Single<RepoListResponse>

    @GET("users/{id}")
    fun getUserInfo(@Path("id") userUrl: String): Single<UserInfoResponse>

    @GET("repos/{repoUrl}")
    fun getRepoInfo(@Path("repoUrl", encoded = true) repoUrl: String): Single<RepoInfoResponse>
}