package com.example.mashuparchitecture.data.source

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse

interface Repository {
    //remote
    fun getGithubRepositories(
        query: String,
        onSuccess: (data: GithubRepositoriesResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )
}