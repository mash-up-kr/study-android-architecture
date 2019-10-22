package com.namget.myarchitecture.data.api

import com.namget.myarchitecture.data.api.response.UserListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Namget on 2019.10.22.
 */
interface ApiService {

    @GET("search/users")
    fun getUserList(@Query("q") searchName: String) : Single<UserListResponse>
}