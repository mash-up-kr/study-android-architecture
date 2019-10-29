package com.runeanim.mytoyproject.data.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
)
