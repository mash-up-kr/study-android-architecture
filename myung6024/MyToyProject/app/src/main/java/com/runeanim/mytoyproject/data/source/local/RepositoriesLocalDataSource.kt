package com.runeanim.mytoyproject.data.source.local

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Error
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoriesLocalDataSource internal constructor(
    private val repositoriesDao: RepositoriesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getRepositories(): Result<List<RepositoryEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(repositoriesDao.getRepositories())
        } catch (e: Exception) {
            Error(e)
        }
    }

    suspend fun saveRepository(repositoryEntity: RepositoryEntity) = withContext(ioDispatcher) {
        repositoriesDao.insertRepository(repositoryEntity)
    }
}