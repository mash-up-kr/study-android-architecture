package com.example.myapplication.Network

import com.example.myapplication.Data.SearchRepo
import com.example.myapplication.Network.Get.SearchRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    
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