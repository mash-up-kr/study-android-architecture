package com.example.mashuparchitecture.data.source

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import io.reactivex.Single

interface Repository {
    //remote
    fun getUserData(login: String): Single<GithubUserResponse>

    //remote
    fun getGithubRepositories(query: String): Single<GithubRepositoriesResponse>

}