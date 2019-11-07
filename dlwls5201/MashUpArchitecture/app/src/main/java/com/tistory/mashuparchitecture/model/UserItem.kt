package com.tistory.mashuparchitecture.model

import com.tistory.blackjin.domain.entity.UserEntity
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.di.ResourcesProvider

data class UserItem(
    val name: String,
    val profileUrl: String,
    val followers: String,
    val following: String
)

fun UserEntity.mapToPresentation(resources: ResourcesProvider) = let {
    UserItem(
        name = if (it.name.isNullOrEmpty())
            resources.getString(R.string.unknown)
        else
            it.name!!,

        profileUrl = it.profileUrl,

        followers = if (it.followers > 999)
            "999+"
        else
            it.followers.toString()
        ,
        following = if (it.following > 999)
            "999+"
        else
            it.following.toString()
    )
}
