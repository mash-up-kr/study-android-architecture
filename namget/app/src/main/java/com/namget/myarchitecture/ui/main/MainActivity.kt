package com.namget.myarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.namget.myarchitecture.R
import com.namget.myarchitecture.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    val UserListResponse = com.namget.myarchitecture.data.api.response.UserListResponse()


    private fun init() {
        UserListResponse.
        initView()
    }

    private fun initView() {
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }


}
