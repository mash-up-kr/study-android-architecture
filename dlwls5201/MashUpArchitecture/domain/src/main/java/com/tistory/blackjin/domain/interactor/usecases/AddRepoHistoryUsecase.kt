package com.tistory.blackjin.domain.interactor.usecases

import com.tistory.blackjin.domain.entity.RepoHistoryEntity
import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Completable

class AddRepoHistoryUsecase(
    private val repoRepository: RepoRepository,
    private val schedulersProvider: SchedulersProvider
) {
    fun get(repo: RepoHistoryEntity) {
        Completable.fromCallable {
            repoRepository.add(repo)
        }.subscribeOn(
            schedulersProvider.io()
        ).subscribe()
    }
}