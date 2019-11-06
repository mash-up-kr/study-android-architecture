package com.namget.myarchitecture.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Namget on 2019.10.23.
 */
data class UserInfoResponse(
    val name: String,
    val followers: Int,
    val following: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
)