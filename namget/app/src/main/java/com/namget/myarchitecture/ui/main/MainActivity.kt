package com.namget.myarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import com.namget.myarchitecture.R
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    val UserListResponse = com.namget.myarchitecture.data.api.response.RepoListResponse()


    private fun init() {
        initView()
    }

    private fun initView() {
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }


}
