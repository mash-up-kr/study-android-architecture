package com.example.mashuparchitecture.network.vo


import com.example.mashuparchitecture.data.source.vo.DetailRepoVo
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class GithubRepositoriesResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>
) {
    data class Item(
        @SerializedName("id")
        val id: Int,
        @SerializedName("node_id")
        val nodeId: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("owner")
        val owner: Owner,
        @SerializedName("private")
        val `private`: Boolean,
        @SerializedName("html_url")
        val htmlUrl: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("fork")
        val fork: Boolean,
        @SerializedName("url")
        val url: String,
        @SerializedName("created_at")
        val createdAt: Date?,
        @SerializedName("updated_at")
        val updatedAt: Date?,
        @SerializedName("pushed_at")
        val pushedAt: Date?,
        @SerializedName("homepage")
        val homepage: String,
        @SerializedName("size")
        val size: Int,
        @SerializedName("stargazers_count")
        val stargazersCount: Int,
        @SerializedName("watchers_count")
        val watchersCount: Int,
        @SerializedName("language")
        val language: String?,
        @SerializedName("forks_count")
        val forksCount: Int,
        @SerializedName("open_issues_count")
        val openIssuesCount: Int,
        @SerializedName("master_branch")
        val masterBranch: String,
        @SerializedName("default_branch")
        val defaultBranch: String,
        @SerializedName("score")
        val score: Double
    ) : Serializable {
        data class Owner(
            @SerializedName("login")
            val login: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("node_id")
            val nodeId: String,
            @SerializedName("avatar_url")
            val avatarUrl: String,
            @SerializedName("gravatar_id")
            val gravatarId: String,
            @SerializedName("url")
            val url: String,
            @SerializedName("received_events_url")
            val receivedEventsUrl: String,
            @SerializedName("type")
            val type: String
        ) : Serializable

        fun convertItemIntoDetailRepoVo(
            githubUserResponse: GithubUserResponse
        ): DetailRepoVo {

            val updatedAtStr = if (updatedAt != null) SimpleDateFormat(
                "yyyy-MM-dd E요일 HH:mm",
                Locale.KOREA
            ).format(updatedAt) else "No update time specified"

            return DetailRepoVo(
                id,
                name,
                fullName,
                owner,
                description,
                updatedAtStr,
                "★ $stargazersCount stars",
                watchersCount,
                language ?: "No language specified",
                githubUserResponse.name ?: "No name specified",
                "Followers : ${githubUserResponse.followers}",
                "Following : ${githubUserResponse.following}"
            )
        }
    }
}