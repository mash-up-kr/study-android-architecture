package com.example.mashuparchitecture.ui.detail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.RepositoryImpl
import com.example.mashuparchitecture.data.source.local.LocalDataSourceImpl
import com.example.mashuparchitecture.data.source.remote.RemoteDataSourceImpl
import com.example.mashuparchitecture.databinding.ActivityDetailBinding
import com.example.mashuparchitecture.network.RetrofitHelper
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val repository by lazy {
        RepositoryImpl(
            RemoteDataSourceImpl(RetrofitHelper.getInstance().apiService),
            LocalDataSourceImpl()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadData()
    }

    private fun loadData() {
        showProgressBar()
        val repo = (intent.getSerializableExtra("item") as GithubRepositoriesResponse.Item)

        repository
            .getUserData(repo.owner.login, { userDataResponse ->
                if (userDataResponse != null) {
                    bindRepoAndUserData(userDataResponse, repo)
                }
                hideProgressBar()
            }, {
                showToastMessage(it)
                hideProgressBar()
            })
    }

    private fun bindRepoAndUserData(
        userData: GithubUserResponse,
        repo: GithubRepositoriesResponse.Item
    ) {
        val detailRepoVo = repo.convertItemIntoDetailRepoVo(userData)

        with(binding) {
            tvTitle.text = detailRepoVo.fullName
            tvStars.text = detailRepoVo.stargazersCount
            tvUserName.text = detailRepoVo.userName
            tvUserFollowers.text = detailRepoVo.userFollowers
            tvUserFollowing.text = detailRepoVo.userFollowing
            tvDescriptionVal.text = detailRepoVo.description
            tvLanguageVal.text = detailRepoVo.language
            tvLastUpdateVal.text = detailRepoVo.updatedAt
        }

        Glide
            .with(binding.root)
            .load(detailRepoVo.owner.avatarUrl)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(binding.ivAvatar)
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
