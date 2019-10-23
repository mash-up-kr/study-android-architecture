package com.namget.myarchitecture.data.api

import com.namget.myarchitecture.data.api.response.RepoListResponse
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

    @GET("user/{id}")
    fun getUserInfo(@Path("id") id: String): Single<RepoListResponse>

    @GET("search/repositories")
    fun getRepoInfo(@Query("q") searchName: String): Single<RepoListResponse>
}