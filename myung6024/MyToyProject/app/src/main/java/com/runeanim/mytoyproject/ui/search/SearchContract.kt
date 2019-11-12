package com.runeanim.mytoyproject.ui.search

import com.runeanim.mytoyproject.base.BasePresenter
import com.runeanim.mytoyproject.base.BaseView
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun setupListAdapter()

        fun hideSoftKeyBoard()

        fun moveScreenToDetailFragment(repositoryEntity: RepositoryEntity)
    }

    interface Presenter : BasePresenter {

        fun onPause()

        fun onClickRepositoryItem(repositoryEntity: RepositoryEntity)

        val searchRepositoryByKeyWord: (String) -> Unit
    }
}