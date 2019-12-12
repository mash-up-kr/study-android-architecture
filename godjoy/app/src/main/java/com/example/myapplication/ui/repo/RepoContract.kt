package com.example.myapplication.ui.repo

import com.example.myapplication.model.SearchRepo
import com.example.myapplication.ui.Base.BaseContract

interface RepoContract {

    interface View : BaseContract.View{
        fun loadRepoData(repoData : SearchRepo?)
    }

    interface Presenter : BaseContract.Presenter{
        fun requestRepoData(userName : String, repoName : String)
    }
}