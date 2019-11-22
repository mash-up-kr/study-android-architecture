package com.example.mashuparchitecture.ui.detail

import com.example.mashuparchitecture.base.BasePresenter
import com.example.mashuparchitecture.base.BaseView
import com.example.mashuparchitecture.data.source.vo.GithubDetailRepoVo
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity

interface DetailContract {
    interface View: BaseView<Presenter> {
        fun showDetailRepo(repo: GithubDetailRepoVo)

        fun showProgressBar()

        fun hideProgressBar()
    }

    interface Presenter: BasePresenter {
        fun loadData(repo: GithubRepoEntity)
    }
}