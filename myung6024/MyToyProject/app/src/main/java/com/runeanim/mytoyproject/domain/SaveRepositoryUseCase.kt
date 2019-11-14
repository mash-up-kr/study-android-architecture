package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource

class SaveRepositoryUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(repositoryEntity: RepositoryEntity) {
        wrapEspressoIdlingResource {
            return repositoriesRepository.saveRepository(repositoryEntity)
        }
    }
}