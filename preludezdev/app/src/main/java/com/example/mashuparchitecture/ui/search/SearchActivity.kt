package com.example.mashuparchitecture.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.databinding.ActivitySearchBinding
import com.example.mashuparchitecture.ui.detail.DetailActivity
import org.koin.android.ext.android.inject

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val adapter by lazy { GithubAdapter { clickCallback(it) } }
    private val repository: Repository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        initEvent()
    }

    private fun clickCallback(position: Int) {
        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", adapter.getItem(position))
            })
    }

    private fun initRecyclerView() {
        binding.rvSearch.adapter = adapter
    }

    private fun initEvent() {
        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                hideKeyBoard()
                searchQuery(binding.etSearch.text.toString())
            }

            false
        }

        binding.ivSearch.setOnClickListener {
            hideKeyBoard()
            searchQuery(binding.etSearch.text.toString())
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchQuery(query: String?) {
        if (query.isNullOrEmpty()) {
            showToastMessage("검색어를 입력해주세요.")
            return
        }

        showProgressBar()

        repository
            .getGithubRepositories(query, { response ->
                if (response != null) {
                    adapter.setData(response.items)
                }

                hideProgressBar()
            }, {
                showToastMessage(it)
                hideProgressBar()
            })
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun showProgressBar() {
        with(binding) {
            rvSearch.visibility = View.INVISIBLE
            pbSearch.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        with(binding) {
            rvSearch.visibility = View.VISIBLE
            pbSearch.visibility = View.INVISIBLE
        }
    }

}
