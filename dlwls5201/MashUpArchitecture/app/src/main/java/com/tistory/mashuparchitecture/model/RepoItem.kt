package com.tistory.mashuparchitecture.model

import com.tistory.blackjin.domain.entity.RepoEntity

data class RepoItem(
    val title: String,
    val repoName: String,
    val owner: OwnerItem,
    val description: String?,
    val language: String?,
    val updatedAt: String,
    val stars: String
) {
    data class OwnerItem(
        val ownerName: String,
        val ownerUrl: String
    )
}

fun List<RepoEntity>.mapToPresentation(): List<RepoItem> =
    map { it.mapToPresentation() }

fun RepoEntity.mapToPresentation() = let {
    RepoItem(
        title = it.repoName + "/" + it.owner.ownerName,
        repoName = it.repoName,
        owner = it.owner.mapToPresentation(),
        description = it.description,
        language = it.language,
        updatedAt = it.updatedAt,
        stars = it.stars.toString()
        )
}

fun RepoEntity.OwnerEntity.mapToPresentation() =
    RepoItem.OwnerItem(
        ownerName = ownerName,
        ownerUrl = ownerUrl
    )
