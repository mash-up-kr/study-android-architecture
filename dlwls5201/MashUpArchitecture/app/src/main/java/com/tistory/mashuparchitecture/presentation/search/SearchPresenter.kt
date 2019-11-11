package com.tistory.mashuparchitecture.presentation.search

import com.tistory.blackjin.domain.error.ErrorEntity
import com.tistory.blackjin.domain.interactor.usecases.GetReposUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.di.ResourcesProvider
import com.tistory.mashuparchitecture.model.mapToPresentation
import io.reactivex.disposables.CompositeDisposable

class SearchPresenter(
    private val view: SearchContract.View,
    private val getReposUsecase: GetReposUsecase,
    private val resourcesProvider: ResourcesProvider
) : SearchContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun searchRepository(query: String) {

        getReposUsecase.get(query)
            .map { it.mapToPresentation(resourcesProvider) }
            .doOnSubscribe {
                view.hideError()
                view.showProgress()
            }
            .doOnSuccess {
                view.hideProgress()
            }
            .doOnError {
                view.hideProgress()
            }
            .subscribe({

                view.showRepos(it)
                view.showTopTitle(query)
                view.showCollapseSearchView()
                view.hideSoftKeyboard()

                if (it.isEmpty()) {
                    view.showError(resourcesProvider.getString(R.string.no_search_result))
                }

            }) {
                showHandleException(it)
            }.also {
                compositeDisposable.add(it)
            }
    }

    override fun destroyView() {
        compositeDisposable.clear()
    }

    private fun showHandleException(throwable: Throwable) {
        when (throwable) {
            is ErrorEntity.RateLimitException -> view.showError(resourcesProvider.getString(R.string.rate_limit_error))
            is ErrorEntity.NetworkException -> view.showError(resourcesProvider.getString(R.string.network_error))
            else -> view.showError(resourcesProvider.getString(R.string.unexpected_error))
        }
    }
}