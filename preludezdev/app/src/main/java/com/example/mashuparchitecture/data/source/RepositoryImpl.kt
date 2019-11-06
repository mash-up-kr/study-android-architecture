package com.example.mashuparchitecture.data.source

import com.example.mashuparchitecture.data.source.local.LocalDataSource
import com.example.mashuparchitecture.data.source.remote.RemoteDataSource
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {
    override fun getUserData(
        login: String?,
        onSuccess: (data: GithubUserResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.getUserData(login, onSuccess, onFail)
    }

    override fun getGithubRepositories(
        query: String?,
        onSuccess: (data: GithubRepositoriesResponse?) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.getGithubRepositories(query, onSuccess, onFail)
    }

}