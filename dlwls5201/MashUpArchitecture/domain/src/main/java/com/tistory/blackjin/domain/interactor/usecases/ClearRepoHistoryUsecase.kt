package com.tistory.blackjin.domain.interactor.usecases

import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Completable

class ClearRepoHistoryUsecase(
    private val repoRepository: RepoRepository,
    private val schedulersProvider: SchedulersProvider
) {
    fun get() {
        Completable.fromCallable {
            repoRepository.clearAll()
        }.subscribeOn(
            schedulersProvider.io()
        ).subscribe()
    }
}