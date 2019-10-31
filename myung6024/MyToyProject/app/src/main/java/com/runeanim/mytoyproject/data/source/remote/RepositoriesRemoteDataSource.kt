package com.runeanim.mytoyproject.data.source.remote

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Error
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.remote.api.GitHubAPI
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class RepositoriesRemoteDataSource internal constructor(
    private val gitHubAPI: GitHubAPI,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(gitHubAPI.searchRepositories(searchKeyWord))
            } catch (e: Exception) {
                Error(e)
            }
        }

    suspend fun getRepositoryInfo(repoUrl: String): Result<Repository> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(gitHubAPI.getRepositoryInfo(repoUrl))
            } catch (e: Exception) {
                Error(e)
            }
        }

    suspend fun getUserInfo(userId: String): Result<Owner> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(gitHubAPI.getUserInfo(userId))
            } catch (e: Exception) {
                Error(e)
            }
        }

}
