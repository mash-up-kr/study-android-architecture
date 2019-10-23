package com.namget.myarchitecture.data.api.response

import com.google.gson.annotations.SerializedName

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

    data class RepoItem(
        @SerializedName("full_name")
        val fullName: String,
        val language: String,
        val url: String,
        val owner: Owner
    )

    data class Owner(
        @SerializedName("avatar_url")
        val avatarUrl: String,
        @SerializedName("url")
        val userUrl: String
    )
}