package com.example.myapplication.ui.search
import com.example.myapplication.model.SearchRepoResponse
import com.example.myapplication.ui.Base.BaseContract

interface SearchContract{

    interface View : BaseContract.View{
        fun loadSearchData(searchData: SearchRepoResponse?)
    }

    interface Presenter : BaseContract.Presenter{
        fun requestSearchData(query : String)
    }
}