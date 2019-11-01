package com.tistory.blackjin.data.source.remote

import com.tistory.blackjin.data.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/{name}")
    fun getUser(@Path("name") repoName: String): Single<User>
}