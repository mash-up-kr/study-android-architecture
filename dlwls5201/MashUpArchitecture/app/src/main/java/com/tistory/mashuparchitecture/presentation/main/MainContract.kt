package com.tistory.mashuparchitecture.presentation.main

import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.presentation.BasePresenter
import com.tistory.mashuparchitecture.presentation.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {

        fun showRepoHistory(repos: List<RepoItem>)

        fun showToast(message: String)

        fun showClearRepoHistory()

        fun showEmptyMessage()

        fun hideEmptyMessage()
    }

    interface Presenter : BasePresenter {

        fun getRepoHistory()

        fun clearAll()

        fun destroyView()
    }

}