package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.source.RepositoriesRepository

class RemoveAllRepositoriesUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke() {
        return repositoriesRepository.removeAllRepositories()
    }
}
