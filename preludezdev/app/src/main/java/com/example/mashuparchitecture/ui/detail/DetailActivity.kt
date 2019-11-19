package com.example.mashuparchitecture.ui.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.vo.GithubDetailRepoVo
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.example.mashuparchitecture.databinding.ActivityDetailBinding
import com.example.mashuparchitecture.ui.search.SearchActivity.Companion.ITEM_KEY
import org.koin.android.ext.android.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail),
    DetailContract.View {

    private val repository: Repository by inject()
    private lateinit var detailPresenter: DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPresenter()
        loadData()
    }

    override fun setPresenter() {
        detailPresenter = DetailPresenter(repository, this)
    }

    private fun loadData() {
        detailPresenter.loadData(intent.getParcelableExtra(ITEM_KEY) as GithubRepoEntity)
    }

    override fun showDetailRepo(repo: GithubDetailRepoVo) {
        binding.repo = repo
    }

    override fun showProgressBar() {
        with(binding) {
            conDetail.visibility = View.INVISIBLE
            pbDetail.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        with(binding) {
            conDetail.visibility = View.VISIBLE
            pbDetail.visibility = View.INVISIBLE
        }
    }

    override fun showToastMessageFromView(@StringRes resId: Int) {
        showToastMessage(resId)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter.dispose()
    }
}
