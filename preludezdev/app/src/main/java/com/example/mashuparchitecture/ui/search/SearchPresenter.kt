package com.example.mashuparchitecture.ui.search

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
            searchView.showToastMessageFromView("검색어를 입력해주세요.")
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
                    searchView.showToastMessageFromView("네트워크 통신에 실패했습니다.")
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
                .subscribe({}, { searchView.showToastMessageFromView("로컬 데이터 삽입에 실패했습니다.") }
                )
        )
    }

    override fun subscribe() {
        searchQuery(currQuery)
    }

    override fun unSubscribe() {
        compositeDisposable.clear()
    }

}