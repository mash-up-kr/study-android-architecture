package com.namget.myarchitecture.ui.repo

import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseDisposable

/**
 * Created by Namget on 2019.11.11.
 */
class RepoPresenter(
    private val repoRepository: RepoRepository,
    private val repoView: RepoContract.View
) : BaseDisposable(), RepoContract.Presenter {

    override fun unsubscribe() {
        disposable.dispose()
    }
    override fun requestUserData(userUrl: String, repoUrl: String) {
        repoView.showDialog()
        disposable += repoRepository.getProfileInfo(userUrl, repoUrl)
            .subscribe({
                repoView.showUserInfoData(it.first)
                repoView.showRepoInfoData(it.second)
                repoView.hideDialog()
            }, {
                repoView.makeToast(R.string.error)
                e(TAG, "requestUserData", it)
                repoView.hideDialog()
            })
    }

    companion object {
        private const val TAG = "RepoPresenter"
    }

}