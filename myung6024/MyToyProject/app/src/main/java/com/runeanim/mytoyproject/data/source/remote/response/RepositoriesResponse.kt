package com.runeanim.mytoyproject.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.model.mapToPresentation

data class RepositoriesResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val repositories: ArrayList<Repository>
)

fun RepositoriesResponse.mapToPresentation() =
    repositories.map { it.mapToPresentation() }
