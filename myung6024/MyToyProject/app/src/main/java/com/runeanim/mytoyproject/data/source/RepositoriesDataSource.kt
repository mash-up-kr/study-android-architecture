package com.runeanim.mytoyproject.data.source

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse

interface RepositoriesDataSource {
    suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse>
}
