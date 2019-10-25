package com.namget.myarchitecture.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity

/**
 * Created by Namget on 2019.10.22.
 */
data class RepoListResponse(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_result")
    val inCompleteResult: Boolean = false,
    val items: List<RepoItem>? = null
) {
    @Entity(tableName = "RepoItem")
    data class RepoItem(
        @PrimaryKey
        @SerializedName("full_name")
        val fullName: String,
        val language: String,
        val url: String,
        val owner: Owner
    ) {
        fun toRepoEntity(): RepoItemEntity = RepoItemEntity(fullName, language, owner.avatarUrl)
    }

    data class Owner(
        @SerializedName("avatar_url")
        val avatarUrl: String,
        @SerializedName("login")
        val login: String
    )
}