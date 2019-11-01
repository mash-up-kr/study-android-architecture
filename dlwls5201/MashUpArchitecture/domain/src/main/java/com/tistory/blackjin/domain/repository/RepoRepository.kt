package com.tistory.blackjin.domain.repository

import com.tistory.blackjin.domain.entity.RepoEntity
import io.reactivex.Single

interface RepoRepository {

    fun searchRepos(repo: String): Single<List<RepoEntity>>

    fun getRepo(id: String, name: String): Single<RepoEntity>
}
