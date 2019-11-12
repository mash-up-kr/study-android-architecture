package com.tistory.blackjin.domain.entity

data class RepoHistoryEntity(
    val repoName: String,
    val ownerName: String,
    val profileUrl: String,
    val language: String?
)