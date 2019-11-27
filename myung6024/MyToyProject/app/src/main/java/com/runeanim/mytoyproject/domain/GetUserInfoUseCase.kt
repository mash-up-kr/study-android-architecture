package com.runeanim.mytoyproject.domain

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource

class GetUserInfoUseCase(
    private val repositoriesRepository: RepositoriesRepository
) {
    suspend operator fun invoke(userId: String): Result<Owner> {
        wrapEspressoIdlingResource {
            return repositoriesRepository.getUserInfo(userId)
        }
    }
}
