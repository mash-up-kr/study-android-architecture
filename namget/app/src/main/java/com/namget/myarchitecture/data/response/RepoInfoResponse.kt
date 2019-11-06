package com.namget.myarchitecture.data.response

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Namget on 2019.10.23.
 */
data class RepoInfoResponse(
    @SerializedName("full_name")
    val fullName: String,
    val name : String,
    val description: String,
    val language: String?,
    @SerializedName("updated_at")
    val updateTime: Date,
    @SerializedName("stargazers_count")
    val starCount: Int
)