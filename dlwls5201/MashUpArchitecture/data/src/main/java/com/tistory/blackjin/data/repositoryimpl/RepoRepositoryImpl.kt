package com.tistory.blackjin.data.repositoryimpl

import com.tistory.blackjin.data.model.mapToDomain
import com.tistory.blackjin.data.source.remote.RepoApi
import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.repository.RepoRepository
import io.reactivex.Single

class RepoRepositoryImpl(
    private val repoApi: RepoApi
) : RepoRepository {

    override fun searchRepos(repo: String): Single<List<RepoEntity>> {
        return repoApi.searchRepository(repo).map { it.items.mapToDomain() }
    }

    override fun getRepo(id: String, name: String): Single<RepoEntity> {
        return repoApi.getRepository(id, name).map { it.mapToDomain() }
    }
}