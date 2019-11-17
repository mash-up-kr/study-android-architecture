package com.example.myapplication.Data

import com.google.gson.annotations.SerializedName
import java.util.*

data class SearchRepo(
    val name : String,
    @SerializedName("full_Name") val fullName : String,
    val owner : Owner,
    val description : String?,
    val language : String?,
    @SerializedName("updated_at") val updateTime : String,
    @SerializedName("stargazers_count") val starNum : Int
)

data class Owner(
    @SerializedName("login") val userName : String, //이름
    @SerializedName("avatar_url") val userImg : String //사진
)