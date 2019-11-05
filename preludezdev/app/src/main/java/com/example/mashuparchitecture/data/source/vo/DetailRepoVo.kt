package com.example.mashuparchitecture.data.source.vo

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.google.gson.annotations.SerializedName

data class DetailRepoVo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("owner")
    val owner: GithubRepositoriesResponse.Item.Owner,
    @SerializedName("description")
    val description: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: String,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("language")
    val language: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_followers")
    val userFollowers: String,
    @SerializedName("user_following")
    val userFollowing: String
)