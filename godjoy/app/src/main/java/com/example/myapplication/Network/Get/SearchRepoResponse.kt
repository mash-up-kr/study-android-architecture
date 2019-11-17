package com.example.myapplication.Network.Get

import com.example.myapplication.Data.SearchRepo
import com.google.gson.annotations.SerializedName

class SearchRepoResponse (
    @SerializedName("total_count") val totalCount : Int,
    val items : ArrayList<SearchRepo>?
)