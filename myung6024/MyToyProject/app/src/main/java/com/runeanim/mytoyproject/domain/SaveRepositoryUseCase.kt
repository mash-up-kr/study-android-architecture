package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

class SaveRepositoryUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(repositoryEntity: RepositoryEntity) {
        return repositoriesRepository.saveRepository(repositoryEntity)
    }
}