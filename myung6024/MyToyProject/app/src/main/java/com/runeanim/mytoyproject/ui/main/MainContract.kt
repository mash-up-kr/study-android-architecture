package com.runeanim.mytoyproject.ui.main

import com.runeanim.mytoyproject.base.BasePresenter
import com.runeanim.mytoyproject.base.BaseView
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

interface MainContract {

    interface View : BaseView<Presenter> {

        fun setupListAdapter()

        fun moveScreenToSearchFragment()

        fun moveScreenToDetailFragment(repositoryEntity: RepositoryEntity)
    }

    interface Presenter : BasePresenter {

        fun getClickedRepositories()

        fun onClickListItem(repositoryEntity: RepositoryEntity)

        fun onClickRemoveAllFloatingButton()

        fun onClickSearchFloatingButton()
    }
}