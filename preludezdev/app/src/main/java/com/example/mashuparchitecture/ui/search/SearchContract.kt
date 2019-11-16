package com.example.mashuparchitecture.ui.search

import com.example.mashuparchitecture.base.BasePresenter
import com.example.mashuparchitecture.base.BaseView
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity

interface SearchContract {
    interface View : BaseView<Presenter>{

        fun showSearchedData(data: List<GithubRepoEntity>)

        fun showProgressBar()

        fun hideProgressBar()

    }

    interface Presenter: BasePresenter{
        fun searchQuery(query: String?)

        fun insertRepo(repo: GithubRepoEntity)
    }
}