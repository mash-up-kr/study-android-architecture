package com.example.myapplication.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewDebug
import com.bumptech.glide.Glide
import com.example.myapplication.Data.SearchRepo
import com.example.myapplication.Network.ApiController
import com.example.myapplication.Network.Get.SearchRepoResponse
import com.example.myapplication.Network.NetworkService
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_repo.*
import kotlinx.coroutines.GlobalScope
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : AppCompatActivity() {
    lateinit var login : String
    lateinit var repoName : String
    val networkService : NetworkService by lazy {
        ApiController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        getName()
        getRepoResponse(login, repoName)
        
    }
    
    fun getName(){
        val intent = intent
        login = intent.getStringExtra("login")
        repoName = intent.getStringExtra("repoName")
    }

    fun getRepoResponse(login : String, repoName : String){

        val getRepoResponse = networkService.getRepoResponse(login, repoName)
        getRepoResponse.enqueue(object : Callback<SearchRepo>{
            override fun onFailure(call: Call<SearchRepo>, t: Throwable) {
            }

            override fun onResponse(call: Call<SearchRepo>, response: Response<SearchRepo>) {
                if(response.isSuccessful){
                    response.body().let {
                        Glide
                            .with(this@RepoActivity)
                            .load(it!!.owner.avatar_url).centerCrop()
                            .into(img_repo_activity)

                        tv_name_repo_activity.text = it.owner.login + "/"
                        tv_repo_name_repo_activity.text = it.name
                        tv_stars_repo_activity.text = it.stargazers_count.toString() + " stars"

                        tv_language_repo_activity.text =
                            if(it.language.isNullOrEmpty())
                                "No language specified"
                            else it.language

                        tv_description_repo_activity.text =
                            if(it.description.isNullOrEmpty())
                                "No description"
                            else it.description

                        tv_updateDate_repo_activity.text = it.updated_at.subSequence(0,10)
                        tv_updateTime_repo_activity.text = it.updated_at.subSequence(11,19)
                    }

                }
                
            }
        })
    }


}
