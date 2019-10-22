package com.namget.myarchitecture.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Namget on 2019.10.22.
 */
open class BaseResponse<T>(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_result")
    val inCompleteResult: Boolean = false,
    val items: List<T>? = null
)