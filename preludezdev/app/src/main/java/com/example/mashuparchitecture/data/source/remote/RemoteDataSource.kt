package com.example.mashuparchitecture.data.source.remote

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse

interface RemoteDataSource {
    fun getUserData(
        login: String?,
        onSuccess: (data: GithubUserResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun getGithubRepositories(
        query: String?,
        onSuccess: (data: GithubRepositoriesResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

}