package com.example.myapplication.Data

import java.util.*

data class SearchRepo(
    val name : String,
    val full_name : String,
    val owner : Owner,
    val description : String?,
    val language : String?,
    val updated_at : String,
    val stargazers_count : Int
)

data class Owner(
    val login : String, //이름
    val avatar_url : String //사진
)