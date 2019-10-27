package com.runeanim.mytoyproject.data.source

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Error
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.source.remote.RepositoriesRemoteDataSource
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultRepositoriesRepository(
    private val repositoriesRemoteDataSource: RepositoriesRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RepositoriesRepository {
    override suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse> {
        return withContext(ioDispatcher) {
            val newTasks = repositoriesRemoteDataSource.searchRepositories(searchKeyWord)
            (newTasks as? Success)?.let {
                return@withContext Success(it.data)
            }
            return@withContext Error(Exception("Illegal state"))
        }
    }
}
