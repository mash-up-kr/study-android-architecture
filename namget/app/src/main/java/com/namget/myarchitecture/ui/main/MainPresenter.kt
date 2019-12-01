package com.namget.myarchitecture.ui.main

import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseDisposable

/**
 * Created by Namget on 2019.11.13.
 */
class MainPresenter(
    private val repoRepository: RepoRepository,
    private val mainView: MainContract.View
) : BaseDisposable(), MainContract.Presenter {



    override fun unsubscribe() {
        disposable.dispose()
    }

    override fun selectRepoData() {
        mainView.showDialog()
        disposable += repoRepository.selectRepoData()
            .subscribe({
                e(TAG, it.toString())
                mainView.replaceRepoItemList(it)
                mainView.hideDialog()
            }, {
                mainView.makeToast(R.string.error)
                e(TAG, "selectRepoData", it)
                mainView.hideDialog()
            })
    }

    companion object {
        private const val TAG = "MainPresenter"
    }
}