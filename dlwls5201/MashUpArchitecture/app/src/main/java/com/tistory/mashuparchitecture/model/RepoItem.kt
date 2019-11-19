package com.tistory.mashuparchitecture.model

import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.entity.RepoHistoryEntity
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.util.ResourcesProvider
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

fun RepoItem.mapToHistoryDomain() =
    RepoHistoryEntity(
        repoName = repoName,
        ownerName = owner.ownerName,
        language = language,
        profileUrl = owner.ownerUrl
    )

fun List<RepoEntity>.mapToPresentation(resources: ResourcesProvider): List<RepoItem> =
    map { it.mapToPresentation(resources) }

fun RepoEntity.mapToPresentation(resources: ResourcesProvider) = let {

    val dateFormatToShow = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
    )

    RepoItem(
        title = it.owner.ownerName + "/" + it.repoName,
        repoName = it.repoName,
        owner = it.owner.mapToPresentation(),

        description = if (it.description.isNullOrEmpty())
            resources.getString(R.string.no_description_provided)
        else
            it.description,

        language = if (it.language.isNullOrEmpty())
            resources.getString(R.string.no_language_specified)
        else
            it.language,

        updatedAt = try {
            dateFormatToShow.format(it.updatedAt)
        } catch (e: IllegalArgumentException) {
            resources.getString(R.string.unknown)
        },

        stars = resources.getQuantityString(R.plurals.star, it.stars, it.stars) ?: "0"
    )
}

fun RepoEntity.OwnerEntity.mapToPresentation() =
    RepoItem.OwnerItem(
        ownerName = ownerName,
        ownerUrl = ownerUrl
    )

fun RepoHistoryEntity.mapToPresentation(resources: ResourcesProvider) = let {
    RepoItem(
        title = it.ownerName + "/" + it.repoName,
        repoName = it.repoName,
        description = "",

        owner = RepoItem.OwnerItem(it.ownerName, it.profileUrl),

        language = if (it.language.isNullOrEmpty())
            resources.getString(R.string.no_language_specified)
        else
            it.language,

        updatedAt = resources.getString(R.string.unknown),
        stars = "0"
    )
}
