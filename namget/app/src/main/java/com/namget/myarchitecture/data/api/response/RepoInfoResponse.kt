package com.namget.myarchitecture.data.api.response

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Namget on 2019.10.23.
 */
data class RepoInfoResponse(
    val description : String,
    val language : String,
    @SerializedName("updated_at")
    val updateTime : Date
)