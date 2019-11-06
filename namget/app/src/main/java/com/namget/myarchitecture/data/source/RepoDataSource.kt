package com.namget.myarchitecture.data.source

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface RepoDataSource {
    fun insertRepoData(repoItem : RepoItemEntity) : Completable
    fun selectRepoData() : Observable<List<RepoItemEntity>>
    fun getRepositoryList(searchName: String): Single<RepoListResponse>
    fun getUserInfo(id: String): Single<UserInfoResponse>
    fun getRepoInfo(repoUrl: String): Single<RepoInfoResponse>
}