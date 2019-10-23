package com.namget.myarchitecture.data.api.response

/**
 * Created by Namget on 2019.10.23.
 */
data class UserInfoResponse(
    val name: String,
    val followers: Int,
    val following: Int
)