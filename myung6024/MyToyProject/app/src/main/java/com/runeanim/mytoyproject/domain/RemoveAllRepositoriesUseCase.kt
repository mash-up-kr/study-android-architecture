package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource

class RemoveAllRepositoriesUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke() {
        wrapEspressoIdlingResource {
            return repositoriesRepository.removeAllRepositories()
        }
    }
}
