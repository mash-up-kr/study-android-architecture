package com.example.mashuparchitecture.network

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Call<GithubRepositoriesResponse>
}