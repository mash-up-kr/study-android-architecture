package com.example.mashuparchitecture.data.source

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse

interface Repository {
    //remote
    fun getUserData(
        login: String,
        onSuccess: (data: GithubUserResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    //remote
    fun getGithubRepositories(
        query: String,
        onSuccess: (data: GithubRepositoriesResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

}