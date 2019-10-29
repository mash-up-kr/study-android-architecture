package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.RepositoriesRepository

class GetRepositoryInfoUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(repoUrl: String): Result<Repository> {
        return repositoriesRepository.getRepositoryInfo(repoUrl)
    }
}
