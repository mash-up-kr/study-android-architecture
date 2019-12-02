package com.namget.myarchitecture.ui.repo

import androidx.databinding.ObservableField
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseViewModel

/**
 * Created by Namget on 2019.12.01.
 */
class RepoViewModel(
    private val repoRepository: RepoRepository
    , private val toastItemCallback: (Int) -> Unit
) : BaseViewModel() {
    val a = ObservableField<Pair<String,String>>()


    fun requestUserData(userUrl: String, repoUrl: String) {
        isLoading.set(true)
        disposable += repoRepository.getProfileInfo(userUrl, repoUrl)
            .subscribe({
                repoView.showUserInfoData(it.first)
                repoView.showRepoInfoData(it.second)
                isLoading.set(false)
            }, {
                repoView.makeToast(R.string.error)
                e(RepoPresenter.TAG, "requestUserData", it)
                isLoading.set(false)
            })
    }
}