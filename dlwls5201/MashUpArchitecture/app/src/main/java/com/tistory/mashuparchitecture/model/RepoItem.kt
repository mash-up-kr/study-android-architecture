package com.tistory.mashuparchitecture.model

import android.content.res.Resources
import android.text.TextUtils
import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.mashuparchitecture.R
import java.text.SimpleDateFormat
import java.util.*

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

fun List<RepoEntity>.mapToPresentation(resources: Resources): List<RepoItem> =
    map { it.mapToPresentation(resources) }

fun RepoEntity.mapToPresentation(resources: Resources) = let {

    val dateFormatInResponse = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault()
    )

    val dateFormatToShow = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
    )

    RepoItem(
        title = it.owner.ownerName + "/" + it.repoName,
        repoName = it.repoName,
        owner = it.owner.mapToPresentation(),

        description = if (TextUtils.isEmpty(it.description))
            resources.getString(R.string.no_description_provided)
        else
            it.description,

        language = if (TextUtils.isEmpty(it.language))
            resources.getString(R.string.no_language_specified)
        else
            it.language,

        updatedAt = try {
            val lastUpdate = dateFormatInResponse.parse(it.updatedAt)
            dateFormatToShow.format(lastUpdate)
        } catch (e: Exception) {
            resources.getString(R.string.unknown)
        },

        stars = resources.getQuantityString(R.plurals.star, it.stars, it.stars)
    )
}

fun RepoEntity.OwnerEntity.mapToPresentation() =
    RepoItem.OwnerItem(
        ownerName = ownerName,
        ownerUrl = ownerUrl
    )
