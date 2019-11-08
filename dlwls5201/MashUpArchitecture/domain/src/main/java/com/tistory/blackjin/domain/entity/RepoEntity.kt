package com.tistory.blackjin.domain.entity

import java.util.*

data class RepoEntity(
    val repoName: String,
    val owner: OwnerEntity,
    val description: String?,
    val language: String?,
    val updatedAt: Date,
    val stars: Int
) {
    data class OwnerEntity(
        val ownerName: String,
        val ownerUrl: String
    )
}