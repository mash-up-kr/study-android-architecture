package com.example.myapplication.ui.search

import com.example.myapplication.model.SearchRepoResponse
import com.example.myapplication.network.GithubApiProvider
import com.example.myapplication.ui.Base.BaseContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(val view: SearchContract.View): SearchContract.Presenter {

    private lateinit var getSearchRepoResponse : Call<SearchRepoResponse>

    override fun start() {
        view.init()
    }

    override fun requestSearchData(query : String) {
        view.hideError()
        view.showProgress()

        getSearchRepoResponse = GithubApiProvider.getService().getSearchRepoResponse(query)
        getSearchRepoResponse.enqueue(object : Callback<SearchRepoResponse>{
            override fun onFailure(call: Call<SearchRepoResponse>, t: Throwable) {
                view.showError()
            }

            override fun onResponse(call: Call<SearchRepoResponse>, response: Response<SearchRepoResponse>) {
                if(response.isSuccessful){
                    view.hideProgress()
                    view.loadSearchData(response.body())
                }else{
                    view.showError()
                }
            }
        })

    }
}