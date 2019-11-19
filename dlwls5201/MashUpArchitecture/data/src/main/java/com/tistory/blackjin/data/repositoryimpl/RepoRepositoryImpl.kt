package com.tistory.blackjin.data.repositoryimpl

import com.tistory.blackjin.data.model.RepoHistory
import com.tistory.blackjin.data.model.mapToDomain
import com.tistory.blackjin.data.source.local.RepoDao
import com.tistory.blackjin.data.source.remote.RepoApi
import com.tistory.blackjin.domain.entity.RepoHistoryEntity
import com.tistory.blackjin.domain.repository.RepoRepository

class RepoRepositoryImpl(
    private val repoApi: RepoApi,
    private val repoDao: RepoDao
) : RepoRepository {

    override fun searchRepos(repo: String) =
        repoApi.searchRepository(repo)
            .map { it.items.mapToDomain() }


    override fun getRepo(ownerLogin: String, repoName: String) =
        repoApi.getRepository(ownerLogin, repoName)
            .map { it.mapToDomain() }


    override fun getRepo() =
        repoDao.getRepos()
            .map { it.mapToDomain() }


    override fun add(repo: RepoHistoryEntity) {
        val repoHistory = RepoHistory(
            repoName = repo.repoName,
            ownerName = repo.ownerName,
            profileUrl = repo.profileUrl,
            language = repo.language
        )
        return repoDao.add(repoHistory)
    }

    override fun clearAll() = repoDao.clearAll()

}