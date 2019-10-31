package com.runeanim.mytoyproject.data.source

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Error
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.local.RepositoriesLocalDataSource
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.data.source.remote.RepositoriesRemoteDataSource
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import kotlinx.coroutines.*
import java.lang.Exception

class DefaultRepositoriesRepository(
    private val repositoriesLocalDataSource: RepositoriesLocalDataSource,
    private val repositoriesRemoteDataSource: RepositoriesRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RepositoriesRepository {

    override suspend fun saveRepository(repositoryEntity: RepositoryEntity) {
        coroutineScope {
            launch { repositoriesLocalDataSource.saveRepository(repositoryEntity) }
        }
    }

    override suspend fun getRepositories(): Result<List<RepositoryEntity>> {
        return withContext(ioDispatcher) {
            val newTasks = repositoriesLocalDataSource.getRepositories()
            (newTasks as? Success)?.let {
                return@withContext Success(it.data)
            }
            return@withContext Error(Exception("Illegal state"))
        }
    }

    override suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse> {
        return withContext(ioDispatcher) {
            val newTasks = repositoriesRemoteDataSource.searchRepositories(searchKeyWord)
            (newTasks as? Success)?.let {
                return@withContext Success(it.data)
            }
            return@withContext Error(Exception("Illegal state"))
        }
    }

    override suspend fun getRepositoryInfo(repoUrl: String): Result<Repository> {
        return withContext(ioDispatcher) {
            val newTasks = repositoriesRemoteDataSource.getRepositoryInfo(repoUrl)
            (newTasks as? Success)?.let {
                return@withContext Success(it.data)
            }
            return@withContext Error(Exception("Illegal state"))
        }
    }

    override suspend fun getUserInfo(userId: String): Result<Owner> {
        return withContext(ioDispatcher) {
            val newTasks = repositoriesRemoteDataSource.getUserInfo(userId)
            (newTasks as? Success)?.let {
                return@withContext Success(it.data)
            }
            return@withContext Error(Exception("Illegal state"))
        }
    }
}
