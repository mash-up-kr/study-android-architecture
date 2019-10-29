package com.runeanim.mytoyproject.data.source

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse

interface RepositoriesDataSource {
    suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse>

    suspend fun getRepositoryInfo(repoUrl: String): Result<Repository>

    suspend fun getUserInfo(userId: String): Result<Owner>
}
