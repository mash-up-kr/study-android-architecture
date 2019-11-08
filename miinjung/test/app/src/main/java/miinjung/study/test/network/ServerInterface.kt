package miinjung.study.test.network

import miinjung.study.test.Model.item
import miinjung.study.test.Model.list
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerInterface {

    @GET("search/repositories")
    fun search(@Query("q") query: String): Call<list>

    @GET("repos/{owner}/{name}")
    fun getRepo(
        @Path("owner") ownerLogin: String,
        @Path("name") repoName: String): Call<item>

}