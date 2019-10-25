package com.namget.myarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import com.namget.myarchitecture.R
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.makeToast
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initView()
        selectRepoData()
    }

    private fun initView() {
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun selectRepoData() {
        disposable += repoRepository.selectRepoData()
            .subscribe({
                e(TAG, it.toString())
            }, {
                makeToast(getString(R.string.error))
                e(TAG, "selectRepoData", it)
            },{
            })
    }


}
