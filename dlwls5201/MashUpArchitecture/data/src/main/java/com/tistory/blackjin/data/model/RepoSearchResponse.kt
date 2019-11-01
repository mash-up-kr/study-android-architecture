package com.tistory.blackjin.data.model

import com.google.gson.annotations.SerializedName
import com.tistory.blackjin.domain.entity.RepoEntity

data class RepoSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<Repo>
)

fun List<Repo>.mapToDomain() = map {
    RepoEntity(
        it.name,
        it.owner.mapToDomain(),
        it.description,
        it.language,
        it.updatedAt,
        it.stars
    )
}