package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource

class GetRepositoriesUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(): Result<List<RepositoryEntity>> {
        wrapEspressoIdlingResource {
            return repositoriesRepository.getRepositories()
        }
    }
}