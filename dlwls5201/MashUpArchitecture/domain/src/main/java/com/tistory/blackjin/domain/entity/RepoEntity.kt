package com.tistory.blackjin.domain.entity

data class RepoEntity(
    val repoName: String,
    val owner: OwnerEntity,
    val description: String?,
    val language: String?,
    val updatedAt: String,
    val stars: Int
) {
    data class OwnerEntity(
        val ownerName: String,
        val ownerUrl: String
    )
}