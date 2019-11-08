package com.tistory.blackjin.data.model

import com.google.gson.annotations.SerializedName
import com.tistory.blackjin.domain.entity.UserEntity

data class User(
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int
)

fun User.mapToDomain() =
    UserEntity(
        name = name,
        profileUrl = avatarUrl,
        followers = followers,
        following = following
    )