package com.example.mashuparchitecture.data.source

import com.example.mashuparchitecture.data.source.local.LocalDataSource
import com.example.mashuparchitecture.data.source.remote.RemoteDataSource
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import io.reactivex.Single

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override fun getUserData(login: String): Single<GithubUserResponse> =
        remoteDataSource.getUserData(login)

    override fun getGithubRepositories(query: String): Single<GithubRepositoriesResponse> {
        return remoteDataSource.getGithubRepositories(query)
    }

}