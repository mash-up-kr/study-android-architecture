package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

class SearchRepoResponse (
    @SerializedName("total_count") val totalCount : Int,
    val items : ArrayList<SearchRepo>?
)