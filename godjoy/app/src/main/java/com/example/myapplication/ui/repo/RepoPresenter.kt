package com.example.myapplication.ui.repo

import com.example.myapplication.model.SearchRepo
import com.example.myapplication.network.GithubApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoPresenter(val view : RepoContract.View): RepoContract.Presenter {

    private lateinit var getRepoResponse  : Call<SearchRepo>

    override fun requestRepoData(userName : String, repoName : String) {
        view.showProgress()

        getRepoResponse =  GithubApiProvider.getService().getRepoResponse(userName, repoName)

        getRepoResponse.enqueue(object : Callback<SearchRepo>{
                override fun onFailure(call: Call<SearchRepo>, t: Throwable) {
                    view.showError()
                }

                override fun onResponse(call: Call<SearchRepo>, response: Response<SearchRepo>) {
                    if(response.isSuccessful){
                        view.hideProgress()
                        view.loadRepoData(response.body())
                    }
                    else{
                        view.hideProgress()
                        view.showError()
                    }
                }
            })
    }

    override fun start() {
        view.init()
    }
}