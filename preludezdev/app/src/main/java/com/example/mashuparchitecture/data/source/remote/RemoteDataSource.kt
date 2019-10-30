package com.example.mashuparchitecture.data.source.remote

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse

interface RemoteDataSource {
    fun getGithubRepositories(
        query: String,
        onSuccess: (data: GithubRepositoriesResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )
}