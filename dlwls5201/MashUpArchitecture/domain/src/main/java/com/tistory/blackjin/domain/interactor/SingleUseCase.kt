package com.tistory.blackjin.domain.interactor

import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Single

abstract class SingleUseCase<T, in Params>(
    private val schedulersProvider: SchedulersProvider
) {
    protected abstract fun buildUseCaseSingle(params: Params): Single<T>

    fun get(params: Params) = buildUseCaseSingle(params)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
}
