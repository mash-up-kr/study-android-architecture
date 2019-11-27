package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource

class SearchRepositoriesUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(searchKeyWord: String): Result<RepositoriesResponse> {
        wrapEspressoIdlingResource {
            return repositoriesRepository.searchRepositories(searchKeyWord)
        }
    }
}
