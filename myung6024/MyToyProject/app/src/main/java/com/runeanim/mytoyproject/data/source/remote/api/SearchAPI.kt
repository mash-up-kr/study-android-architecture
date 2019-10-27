package com.runeanim.mytoyproject.data.source.remote.api

import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("/search/repositories")
    suspend fun search(@Query("q") searchKeyWord: String): RepositoriesResponse

}
