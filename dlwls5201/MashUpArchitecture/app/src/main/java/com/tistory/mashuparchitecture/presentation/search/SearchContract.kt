package com.tistory.mashuparchitecture.presentation.search

import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.presentation.BasePresenter
import com.tistory.mashuparchitecture.presentation.BaseView

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun showRepos(items: List<RepoItem>)

        fun showTopTitle(title: String)

        fun showCollapseSearchView()

        fun hideSoftKeyboard()

        fun showProgress()

        fun hideProgress()

        fun showError(message: String?)

        fun hideError()
    }

    interface Presenter : BasePresenter {

        fun searchRepository(query: String)

        fun destroyView()
    }

}