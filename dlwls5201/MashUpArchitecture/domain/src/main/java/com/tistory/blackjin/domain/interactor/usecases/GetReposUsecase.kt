package com.tistory.blackjin.domain.interactor.usecases

import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.interactor.SingleUseCase
import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Single

class GetReposUsecase(
    private val repoRepository: RepoRepository,
    schedulersProvider: SchedulersProvider
) : SingleUseCase<List<RepoEntity>, String>(
    schedulersProvider
) {
    override fun buildUseCaseSingle(params: String): Single<List<RepoEntity>> {
        return repoRepository.searchRepos(params)
    }
}