package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.Network.model.SearchRepo
import com.example.myapplication.Network.SearchRetrofit
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_repo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : AppCompatActivity(), NetworkException {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        getRepoResponse(SearchRecyclerviewAdapter.queryUserName, SearchRecyclerviewAdapter.queryRepoName)
    }

    fun getRepoResponse(userName : String, repoName : String){
       showProgress(pbActivityRepo)

        val getRepoResponse = SearchRetrofit.getService().getRepoResponse(userName, repoName)
        getRepoResponse.enqueue(object : Callback<SearchRepo>{
            override fun onFailure(call: Call<SearchRepo>, t: Throwable) {
                showError(tvNoResultActivityRepo)
            }

            override fun onResponse(call: Call<SearchRepo>, response: Response<SearchRepo>) {
                if(response.isSuccessful){
                    hideProgress(pbActivityRepo)

                    response.body().let {
                        Glide
                            .with(this@RepoActivity)
                            .load(it!!.owner.userImg).centerCrop()
                            .into(ivRepoActivity)

                        tvNameRepoActivity.text = it.owner.userName + "/"
                        tvRepoNameRepoActivity.text = it.name
                        tvStarNumRepoActivity.text = it.starNum.toString() + " stars"

                        tvLanguageRepoActivity.text =
                            if(it.language.isNullOrEmpty())
                                "No language specified"
                            else it.language

                        tvDescriptionRepoActivity.text =
                            if(it.description.isNullOrEmpty())
                                "No description"
                            else it.description

                        tvUpdateDateRepoActivity.text = it.updateTime.subSequence(0,10)
                        tvUpdateTimeRepoActivity.text = it.updateTime.subSequence(11,19)
                    }
                }
                else{
                    hideProgress(pbActivityRepo)
                    showError(tvNoResultActivityRepo)
                }
            }
        })
    }

    override fun showError(tv: TextView) {
        llRepoActivity.visibility = View.GONE
        super.showError(tv)
    }

}
