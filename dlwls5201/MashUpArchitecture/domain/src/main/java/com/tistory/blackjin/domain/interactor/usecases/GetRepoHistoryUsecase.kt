package com.tistory.blackjin.domain.interactor.usecases

import com.tistory.blackjin.domain.entity.RepoHistoryEntity
import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Flowable

class GetRepoHistoryUsecase(
    private val repoRepository: RepoRepository,
    private val schedulersProvider: SchedulersProvider
) {
    fun get(): Flowable<List<RepoHistoryEntity>> {
        return repoRepository
            .getRepo()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }
}