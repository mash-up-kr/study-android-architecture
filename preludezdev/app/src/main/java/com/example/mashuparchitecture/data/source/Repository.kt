package com.example.mashuparchitecture.data.source

import androidx.lifecycle.LiveData
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
    //remote
    fun getUserData(login: String): Single<GithubUserResponse>

    fun getGithubRepositories(query: String): Single<GithubRepositoriesResponse>

    //local
    fun loadMyGithubRepoList(): LiveData<List<GithubRepoEntity>>

    fun insertRepo(repo: GithubRepoEntity): Completable

}