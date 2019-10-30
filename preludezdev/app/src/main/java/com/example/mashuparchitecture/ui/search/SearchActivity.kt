package com.example.mashuparchitecture.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.databinding.ActivitySearchBinding
import com.example.mashuparchitecture.network.RetrofitHelper
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val adapter by lazy { GithubAdapter() }

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

        RetrofitHelper
            .getInstance()
            .apiService
            .searchRepositories(binding.etSearch.text.toString())
            .enqueue(object : Callback<GithubRepositoriesResponse> {
                override fun onFailure(call: Call<GithubRepositoriesResponse>, t: Throwable) {
                    hideProgressBar()
                    showToastMessage("데이터 요청에 실패했습니다.")
                }

                override fun onResponse(
                    call: Call<GithubRepositoriesResponse>,
                    response: Response<GithubRepositoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            when {
                                data.totalCount == 0 -> showToastMessage("검색 결과가 없습니다.")
                                else -> adapter.setData(data.items)
                            }
                        }
                    } else {
                        showToastMessage("데이터 요청에 실패했습니다.")
                    }

                    hideProgressBar()
                }
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
