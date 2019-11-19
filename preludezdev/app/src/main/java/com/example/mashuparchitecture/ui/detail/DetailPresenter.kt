package com.example.mashuparchitecture.ui.detail

import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailPresenter(
    private val repository: Repository,
    private val detailView: DetailContract.View
) : DetailContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData(repo: GithubRepoEntity) {
        detailView.showProgressBar()

        val query = repo.owner.login
        if (query.isNullOrEmpty()) {
            detailView.showToastMessageFromView(R.string.query_is_null_or_empty)
            return
        }

        compositeDisposable.add(
            repository
                .getDetailRepo(repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ detailRepo ->
                    detailView.showDetailRepo(detailRepo)
                    detailView.hideProgressBar()
                }, {
                    detailView.showToastMessageFromView(R.string.network_error)
                    detailView.hideProgressBar()
                })
        )
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

}