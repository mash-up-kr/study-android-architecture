package com.tistory.mashuparchitecture.presentation.main

import com.tistory.blackjin.domain.interactor.usecases.ClearRepoHistoryUsecase
import com.tistory.blackjin.domain.interactor.usecases.GetRepoHistoryUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.di.ResourcesProvider
import com.tistory.mashuparchitecture.model.mapToPresentation
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(
    private val view: MainContract.View,
    private val getUsecase: GetRepoHistoryUsecase,
    private val clearUsecase: ClearRepoHistoryUsecase,
    private val resourcesProvider: ResourcesProvider
) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getRepoHistory() {
        getUsecase.get()
            .subscribe({ repos ->
                if (repos.isEmpty()) {
                    view.showClearRepoHistory()
                    view.showEmptyMessage()
                } else {
                    view.showRepoHistory(
                        repos.map { it.mapToPresentation(resourcesProvider) }
                    )
                    view.hideEmptyMessage()
                }

            }) {
                view.showToast(it.message ?: resourcesProvider.getString(R.string.unexpected_error))
            }.also {
                compositeDisposable.add(it)
            }
    }

    override fun clearAll() {
        clearUsecase.get()
        view.showClearRepoHistory()
    }

    override fun destroyView() {
        compositeDisposable.clear()
    }
}