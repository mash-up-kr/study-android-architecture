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

fun UserEntity.mapToPresentation(resources: ResourcesProvider) = run {
    UserItem(
        name = if (name.isNullOrEmpty())
            resources.getString(R.string.unknown)
        else
            name!!,

        profileUrl = profileUrl,

        followers = if (followers > 999)
            "999+"
        else
            followers.toString()
        ,
        following = if (following > 999)
            "999+"
        else
            following.toString()
    )
}
