package com.namget.myarchitecture.ui.search

import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.ui.base.BasePresenter
import com.namget.myarchitecture.ui.base.BaseView

/**
 * Created by Namget on 2019.11.11.
 */
interface SearchContract {
    interface View : BaseView<Presenter> {
        fun submitList(list: List<RepoListResponse.RepoItem>?)

    }

    interface Presenter : BasePresenter {

    }
}