package com.runeanim.mytoyproject.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.runeanim.mytoyproject.data.model.Repository

data class RepositoriesResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val repositories: ArrayList<Repository>
)
