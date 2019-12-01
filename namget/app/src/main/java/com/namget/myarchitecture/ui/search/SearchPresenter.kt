package com.namget.myarchitecture.ui.search

import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseDisposable

/**
 * Created by Namget on 2019.11.12.
 */
class SearchPresenter(
    private val repoRepository: RepoRepository,
    private val searchView: SearchContract.View
) : BaseDisposable(), SearchContract.Presenter {

    override fun requestRepoList(query: String) {
        searchView.hideKeyboard()
        searchView.showDialog()
        disposable += repoRepository.getRepositoryList(query)
            .subscribe({
                searchView.submitList(it.items)
                searchView.hideDialog()
            }, {
                searchView.makeToast(R.string.error)
                searchView.hideDialog()
            })
    }

    override fun insertRepoData(repoItem: RepoListResponse.RepoItem) {
        disposable += repoRepository.insertRepoData(repoItem.toRepoEntity())
            .subscribe {
                e(TAG, "inserted")
            }
    }

    override fun unsubscribe() {
        disposable.dispose()
    }

    companion object {
        private const val TAG = "SearchPresenter"
    }
}