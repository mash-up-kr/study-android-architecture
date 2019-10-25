package com.namget.myarchitecture.data.source.local

import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Namget on 2019.10.24.
 */
interface RepoLocalDataSource{
    fun insertRepoData(repoItem : RepoItemEntity) : Completable
    fun selectRepoData() : Observable<List<RepoItemEntity>>
}