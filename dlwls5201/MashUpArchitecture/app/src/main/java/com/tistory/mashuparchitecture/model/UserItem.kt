package com.tistory.mashuparchitecture.model

import android.content.res.Resources
import android.text.TextUtils
import com.tistory.blackjin.domain.entity.UserEntity
import com.tistory.mashuparchitecture.R

data class UserItem(
    val name: String,
    val profileUrl: String,
    val followers: String,
    val following: String
)

fun UserEntity.mapToPresentation(resources: Resources) = let {
    UserItem(
        name = if (TextUtils.isEmpty(it.name))
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
