package com.runeanim.mytoyproject.ui.main

import com.runeanim.mytoyproject.base.BasePresenter
import com.runeanim.mytoyproject.base.BaseView
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

interface MainContract {

    interface View : BaseView {

        fun showRepositoryHistory(result: List<RepositoryEntity>)
    }

    interface Presenter : BasePresenter {

        fun getRepositoryHistory()

        fun removeAllRepositoryHistory()
    }
}