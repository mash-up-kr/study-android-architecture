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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        val clickedRepo = adapter.getItem(position)

        compositeDisposable.add(
            repository
                .insertRepo(clickedRepo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { showToastMessage("로컬 데이터 삽입에 실패했습니다.") })
        )

        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", clickedRepo)
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

        compositeDisposable.add(
            repository
                .getGithubRepositories(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null) {
                        adapter.setData(response.items.map { it.convertItemIntoRepoEntity() })
                    }

                    hideProgressBar()
                }, {
                    showToastMessage("네트워크 통신에 실패했습니다.")
                    hideProgressBar()
                })
        )
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
