package com.tistory.mashuparchitecture.presentation.repo

import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.model.UserItem
import com.tistory.mashuparchitecture.presentation.BasePresenter
import com.tistory.mashuparchitecture.presentation.BaseView

interface RepositoryContract {

    interface View : BaseView<Presenter> {

        fun showRepositoryInfo(user: UserItem, repo: RepoItem)

        fun showProgress()

        fun hideProgress(isSucceed: Boolean)

        fun showError(message: String?)
    }

    interface Presenter : BasePresenter {

        fun loadRepository(login: String, repoName: String)

        fun destroyView()
    }

}