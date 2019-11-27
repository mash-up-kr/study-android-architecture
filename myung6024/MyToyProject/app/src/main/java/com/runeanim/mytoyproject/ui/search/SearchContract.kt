package com.runeanim.mytoyproject.ui.search

import com.runeanim.mytoyproject.base.BasePresenter
import com.runeanim.mytoyproject.base.BaseView
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

interface SearchContract {

    interface View : BaseView {

        fun hideResultMessage()

        fun showResultMessage()

        fun hideProgressBar()

        fun showProgressBar()

        fun showSearchResult(result: List<RepositoryEntity>)
    }

    interface Presenter : BasePresenter {

        fun saveRepositoryClickHistory(repositoryEntity: RepositoryEntity)

        val searchRepositoryByKeyWord: (String) -> Unit
    }
}