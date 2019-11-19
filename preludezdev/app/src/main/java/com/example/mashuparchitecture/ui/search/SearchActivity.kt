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
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.example.mashuparchitecture.databinding.ActivitySearchBinding
import com.example.mashuparchitecture.ui.detail.DetailActivity
import org.koin.android.ext.android.inject

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search),
    SearchContract.View {

    private val adapter by lazy { GithubAdapter { clickCallback(it) } }
    private val repository: Repository by inject()
    private lateinit var searchPresenter: SearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPresenter()
        initRecyclerView()
        initEvent()
    }

    override fun setPresenter() {
        searchPresenter = SearchPresenter(repository, this)
    }

    private fun clickCallback(position: Int) {
        val clickedRepo = adapter.getItem(position)
        searchPresenter.insertRepo(clickedRepo)

        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra(ITEM_KEY, clickedRepo)
            })
    }

    private fun initRecyclerView() {
        binding.rvSearch.adapter = adapter
    }

    private fun initEvent() {
        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                hideKeyBoard()
                searchPresenter.searchQuery(binding.etSearch.text.toString())
            }

            false
        }

        binding.ivSearch.setOnClickListener {
            hideKeyBoard()
            searchPresenter.searchQuery(binding.etSearch.text.toString())
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun showSearchedData(data: List<GithubRepoEntity>) {
        adapter.setData(data)
    }

    override fun showProgressBar() {
        with(binding) {
            rvSearch.visibility = View.INVISIBLE
            pbSearch.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        with(binding) {
            rvSearch.visibility = View.VISIBLE
            pbSearch.visibility = View.INVISIBLE
        }
    }

    override fun showToastMessageFromView(msg: String) {
        showToastMessage(msg)
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.dispose()
    }

    companion object {
        const val ITEM_KEY = "repo"
    }

}
