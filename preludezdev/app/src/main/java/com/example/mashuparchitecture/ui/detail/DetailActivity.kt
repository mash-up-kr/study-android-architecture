package com.example.mashuparchitecture.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseActivity
import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.example.mashuparchitecture.databinding.ActivityDetailBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val repository: Repository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadData()
    }

    private fun loadData() {
        showProgressBar()

        val repo = (intent.getParcelableExtra("item") as GithubRepoEntity)
        val query = repo.owner.login

        if (query.isNullOrEmpty()) {
            showToastMessage("유저 정보가 없습니다.")
            return
        }

        compositeDisposable.add(
            repository
                .getUserData(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userData ->
                    if (userData != null) {
                        Log.d("test", repo.owner.toString())
                        binding.repo = repo.convertItemIntoDetailRepoVo(userData)
                    }

                    hideProgressBar()
                }, {
                    showToastMessage("네트워크 통신에 실패했습니다.")
                    hideProgressBar()
                })
        )
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
