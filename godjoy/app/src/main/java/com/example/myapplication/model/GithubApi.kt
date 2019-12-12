package com.example.myapplication.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    
    @GET("search/repositories")
    fun getSearchRepoResponse(
        @Query("q") query: String
    ):Call<SearchRepoResponse>

    @GET("repos/{owner}/{name}")
    fun getRepoResponse(
        @Path("owner") ownerLogin: String,
        @Path("name") repoName: String
    ): Call<SearchRepo>
}