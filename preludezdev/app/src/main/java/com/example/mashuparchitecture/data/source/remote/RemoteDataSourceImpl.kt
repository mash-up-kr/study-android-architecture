package com.example.mashuparchitecture.data.source.remote

import com.example.mashuparchitecture.network.GithubApiService
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import io.reactivex.Single

class RemoteDataSourceImpl(private val api: GithubApiService) : RemoteDataSource {
    override fun getUserData(login: String): Single<GithubUserResponse> =
        api.getUserData(login)

    override fun getGithubRepositories(query: String): Single<GithubRepositoriesResponse> =
        api.searchRepositories(query)
}