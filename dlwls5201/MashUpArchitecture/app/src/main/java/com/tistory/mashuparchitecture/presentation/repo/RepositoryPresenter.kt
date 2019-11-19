package com.tistory.mashuparchitecture.presentation.repo

import com.tistory.blackjin.domain.error.ErrorEntity
import com.tistory.blackjin.domain.interactor.usecases.GetRepoUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.util.ResourcesProvider
import com.tistory.mashuparchitecture.model.mapToPresentation
import io.reactivex.disposables.CompositeDisposable

class RepositoryPresenter(
    private val view: RepositoryContract.View,
    private val getRepoUsecase: GetRepoUsecase,
    private val resourcesProvider: ResourcesProvider
) : RepositoryContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadRepository(login: String, repoName: String) {
        getRepoUsecase.get(Pair(login, repoName))
            .doOnSubscribe {
                view.showProgress()
            }
            .doOnSuccess {
                view.hideProgress(true)
            }
            .doOnError {
                view.hideProgress(false)
            }
            .subscribe({

                val user = it.first.mapToPresentation(resourcesProvider)
                val repo = it.second.mapToPresentation(resourcesProvider)

                view.showRepositoryInfo(user, repo)

            },::showHandleException).also {
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