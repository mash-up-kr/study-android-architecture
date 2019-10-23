package com.namget.myarchitecture.ui.repo

import android.os.Bundle
import com.namget.myarchitecture.R
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.util.URL_REPO_DATA
import com.namget.myarchitecture.util.URL_USER_DATA

/**
 * Created by Namget on 2019.10.22.
 */
class RepoActivity : BaseActivity() {

    lateinit var repoUrl : String
    lateinit var userUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        init()
    }

    private fun init() {
        getIntentExtra()
    }

    private fun getIntentExtra() {
        if (intent != null && intent.hasCategory(URL_REPO_DATA) && intent.hasCategory(URL_USER_DATA)) {
            repoUrl = intent.getStringExtra(URL_REPO_DATA) ?:""
            userUrl = intent.getStringExtra(URL_USER_DATA) ?: ""
        }
    }

    private fun requestUserData(){

    }


}