package com.example.mashuparchitecture.ui.detail

import com.example.mashuparchitecture.data.source.Repository
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailPresenter(
    private val repository: Repository,
    private val detailView: DetailContract.View
) : DetailContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    lateinit var currRepo : GithubRepoEntity

    override fun loadData(repo : GithubRepoEntity) {
        currRepo = repo
        detailView.showProgressBar()

        val query = currRepo.owner.login

        if (query.isNullOrEmpty()) {
            detailView.showToastMessageFromView("유저 정보가 없습니다.")
            return
        }

        compositeDisposable.add(
            repository
                .getUserData(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userData ->
                    if (userData != null) {
                        detailView.showDetailRepo(currRepo.convertItemIntoDetailRepoVo(userData))
                    }

                    detailView.hideProgressBar()
                }, {
                    detailView.showToastMessageFromView("네트워크 통신에 실패했습니다.")
                    detailView.hideProgressBar()
                })
        )
    }

    override fun subscribe() {
        loadData(currRepo)
    }

    override fun unSubscribe() {
        compositeDisposable.clear()
    }

}