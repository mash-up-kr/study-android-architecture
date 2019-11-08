package com.example.myapplication.Network.Get

import com.example.myapplication.Data.SearchRepo

class SearchRepoResponse (
    val total_count : Int,
    val items : ArrayList<SearchRepo>?
)