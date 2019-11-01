package com.tistory.blackjin.data.model

import com.google.gson.annotations.SerializedName
import com.tistory.blackjin.domain.entity.RepoEntity

data class Repo(
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: OwnerModel,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("stargazers_count")
    val stars: Int
) {
    data class OwnerModel(
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
    )
}

fun Repo.mapToDomain() = RepoEntity(
    name,
    owner.mapToDomain(),
    description,
    language,
    updatedAt,
    stars
)

fun Repo.OwnerModel.mapToDomain() = RepoEntity.OwnerEntity(
    login,
    avatarUrl
)

