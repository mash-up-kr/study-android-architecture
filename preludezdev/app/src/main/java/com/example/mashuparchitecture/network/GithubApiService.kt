package com.example.mashuparchitecture.network

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Single<GithubRepositoriesResponse>

    @GET("users/{username}")
    fun getUserData(@Path("username") id: String): Single<GithubUserResponse>
}