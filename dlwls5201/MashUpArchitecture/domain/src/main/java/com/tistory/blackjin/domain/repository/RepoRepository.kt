package com.tistory.blackjin.domain.repository

import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.entity.RepoHistoryEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface RepoRepository {

    fun searchRepos(repo: String): Single<List<RepoEntity>>

    fun getRepo(ownerLogin: String, repoName: String): Single<RepoEntity>

    fun getRepo(): Flowable<List<RepoHistoryEntity>>

    fun add(repo: RepoHistoryEntity)

    fun clearAll()
}
