package com.namget.myarchitecture.data.repository

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Namget on 2019.10.25.
 */
interface RepoRepository{
    fun getRepositoryList(searchName: String): Single<RepoListResponse>

    fun getProfileInfo(userUrl: String, repoUrl: String): Single<Pair<UserInfoResponse, RepoInfoResponse>>
    fun insertRepoData(repoItem : RepoItemEntity) : Completable
    fun selectRepoData() : Observable<List<RepoItemEntity>>

}