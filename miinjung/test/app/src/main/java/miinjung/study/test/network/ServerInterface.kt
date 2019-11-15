package miinjung.study.test.network

import miinjung.study.test.Model.Item
import miinjung.study.test.Model.List
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerInterface {

    @GET("/search/repositories")
    fun search(@Query("q") query: String): Call<List>

    @GET("/repos/{login}/{name}")
    fun getRepository(
        @Path("login") ownerLogin: String,
        @Path("name") repoName: String): Call<Item>

}