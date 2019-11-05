package com.example.mashuparchitecture.ui.detail

import android.os.Bundle
import android.view.View
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.databinding.ActivityDetailBinding
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import org.koin.android.ext.android.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val repository: Repository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadData()
    }

    private fun loadData() {
        showProgressBar()
        val repo = (intent.getParcelableExtra("item") as GithubRepositoriesResponse.Item)

        repository
            .getUserData(repo.owner.login, { userData ->
                if (userData != null) {
                    binding.repo = repo.convertItemIntoDetailRepoVo(userData)
                }

                hideProgressBar()
            }, {
                showToastMessage(it)
                hideProgressBar()
            })
    }

    private fun showProgressBar() {
        with(binding) {
            conDetail.visibility = View.INVISIBLE
            pbDetail.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        with(binding) {
            conDetail.visibility = View.VISIBLE
            pbDetail.visibility = View.INVISIBLE
        }
    }

}
