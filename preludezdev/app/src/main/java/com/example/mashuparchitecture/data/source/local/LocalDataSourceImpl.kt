package com.example.mashuparchitecture.data.source.local

import androidx.lifecycle.LiveData
import com.example.mashuparchitecture.data.source.local.dao.GithubRepoDao
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import io.reactivex.Completable

class LocalDataSourceImpl(private val dao: GithubRepoDao) : LocalDataSource {
    override fun loadMyGithubRepoList(): LiveData<List<GithubRepoEntity>> =
        dao.loadMyGithubRepoList()

    override fun insertRepo(repo: GithubRepoEntity): Completable = dao.insertRepo(repo)

}