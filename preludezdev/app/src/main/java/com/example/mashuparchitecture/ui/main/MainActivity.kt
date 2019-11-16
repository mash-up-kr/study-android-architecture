package com.example.mashuparchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.databinding.ActivityMainBinding
import com.example.mashuparchitecture.ui.search.SearchActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val repository: Repository by inject()
    private val rvAdapter by lazy { MainRvAdapter() }
    private val myGithubRepoList = repository.loadMyGithubRepoList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        initEvent()
        initCallback()
    }

    private fun initRecyclerView() {
        binding.rvHistory.adapter = rvAdapter
    }

    private fun initEvent() {
        binding.fabSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun initCallback() {
        myGithubRepoList.observe(this, Observer {
            rvAdapter.setData(it)
        })
    }
}
