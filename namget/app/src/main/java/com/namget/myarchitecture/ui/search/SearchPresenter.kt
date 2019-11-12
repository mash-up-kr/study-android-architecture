package com.namget.myarchitecture.ui.search

import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BasePresent

/**
 * Created by Namget on 2019.11.12.
 */
class SearchPresenter(
    private val repoRepository: RepoRepository,
    private val repoSearchView: SearchContract.View,
    private val query: String
) : BasePresent(), SearchContract.Presenter {

    private fun requestRepoList(query: String) {
        disposable += repoRepository.getRepositoryList(query)
            .subscribe({
                repoSearchView.submitList(it.items)
                repoSearchView.hideDialog()
            }, {
                repoSearchView.makeToast(R.string.error)
                repoSearchView.hideDialog()
            })
    }

    override fun subscribe() {
        requestRepoList(query)
    }

    override fun unsubscribe() {
        disposable.clear()
    }
}