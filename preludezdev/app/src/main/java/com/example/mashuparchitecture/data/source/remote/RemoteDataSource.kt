package com.example.mashuparchitecture.data.source.remote

import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun getUserData(login: String): Single<GithubUserResponse>

    fun getGithubRepositories(query: String): Single<GithubRepositoriesResponse>

}