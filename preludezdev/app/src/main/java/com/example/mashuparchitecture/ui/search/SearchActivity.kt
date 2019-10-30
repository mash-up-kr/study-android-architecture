package com.example.mashuparchitecture.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.RepositoryImpl
import com.example.mashuparchitecture.data.source.local.LocalDataSourceImpl
import com.example.mashuparchitecture.data.source.remote.RemoteDataSourceImpl
import com.example.mashuparchitecture.databinding.ActivitySearchBinding
import com.example.mashuparchitecture.network.RetrofitHelper

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val adapter by lazy { GithubAdapter() }
    private val repository by lazy {
        RepositoryImpl(
            RemoteDataSourceImpl(RetrofitHelper.getInstance().apiService),
            LocalDataSourceImpl()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        initEvent()
    }

    private fun initRecyclerView() {
        binding.rvSearch.adapter = adapter
    }

    private fun initEvent() {
        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                hideKeyBoard()
                searchQuery()
            }

            false
        }

        binding.ivSearch.setOnClickListener {
            hideKeyBoard()
            searchQuery()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchQuery() {
        showProgressBar()

        repository
            .getGithubRepositories(binding.etSearch.text.toString(), { response ->
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

    private fun showToastMessage(msg: String?) {
        if (!msg.isNullOrEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
