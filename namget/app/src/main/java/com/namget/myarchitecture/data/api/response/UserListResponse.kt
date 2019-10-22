package com.namget.myarchitecture.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Namget on 2019.10.22.
 */
class UserListResponse : BaseResponse<UserListResponse.RepositoryItem>(){

    data class RepositoryItem(
        @SerializedName("full_name")
        val fullName : String,
        val language : String,
        val url : String,
        val owner : Owner
    )

    data class Owner(
        @SerializedName("avatar_url")
        val avatarUrl: String
    )
}