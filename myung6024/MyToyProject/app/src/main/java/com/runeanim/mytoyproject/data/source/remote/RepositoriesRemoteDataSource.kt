package com.runeanim.mytoyproject.data.source.remote

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Error
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.source.RepositoriesDataSource
import com.runeanim.mytoyproject.data.source.remote.api.SearchAPI
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class RepositoriesRemoteDataSource internal constructor(
    private val searchAPI: SearchAPI,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RepositoriesDataSource {
    override suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(searchAPI.search(searchKeyWord))
            } catch (e: Exception) {
                Error(e)
            }
        }
}
