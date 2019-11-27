package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource

class GetRepositoryInfoUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(repoUrl: String): Result<Repository> {
        wrapEspressoIdlingResource {
            return repositoriesRepository.getRepositoryInfo(repoUrl)
        }
    }
}
