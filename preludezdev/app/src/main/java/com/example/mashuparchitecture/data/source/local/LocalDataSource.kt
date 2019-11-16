package com.example.mashuparchitecture.data.source.local

import androidx.lifecycle.LiveData
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import io.reactivex.Completable

interface LocalDataSource {
    fun loadMyGithubRepoList(): LiveData<List<GithubRepoEntity>>

    fun insertRepo(repo: GithubRepoEntity): Completable

}