package com.example.mashuparchitecture.ui.search

import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private val repository: Repository,
    private val searchView: SearchContract.View
) : SearchContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var currQuery: String

    override fun searchQuery(query: String?) {
        if (query.isNullOrEmpty()) {
            searchView.showToastMessageFromView(R.string.query_is_null_or_empty)
            return
        }

        currQuery = query

        searchView.showProgressBar()

        compositeDisposable.add(
            repository
                .getGithubRepositories(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null) {
                        searchView.showSearchedData(response.items.map { it.convertItemIntoRepoEntity() }
                        )
                    }

                    searchView.hideProgressBar()
                }, {
                    searchView.showToastMessageFromView(R.string.network_error)
                    searchView.hideProgressBar()
                })
        )
    }

    override fun insertRepo(repo: GithubRepoEntity) {
        compositeDisposable.add(
            repository
                .insertRepo(repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { searchView.showToastMessageFromView(R.string.db_error) }
                )
        )
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

}