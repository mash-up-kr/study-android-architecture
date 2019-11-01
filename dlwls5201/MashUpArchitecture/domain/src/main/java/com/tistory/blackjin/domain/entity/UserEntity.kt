package com.tistory.blackjin.domain.entity

data class UserEntity(
    val name: String,
    val profileUrl: String,
    val followers: Int,
    val following: Int
)