package com.example.myapplication.network

import com.example.myapplication.network.model.SearchRepo
import com.example.myapplication.network.model.SearchRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    
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