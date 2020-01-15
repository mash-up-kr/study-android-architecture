package com.namget.myarchitecture.ui.repo

import androidx.databinding.ObservableField
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
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
    val userInfo: ObservableField<UserInfoResponse> = ObservableField()
    val repoInfo: ObservableField<RepoInfoResponse> = ObservableField()

    fun requestUserData(userUrl: String, repoUrl: String) {
        isLoading.set(true)
        disposable += repoRepository.getProfileInfo(userUrl, repoUrl)
            .subscribe({
                userInfo.set(it.first)
                repoInfo.set(it.second)
                isLoading.set(false)
            }, {
                toastItemCallback(R.string.error)
                e(TAG, "requestUserData", it)
                isLoading.set(false)
            })
    }

    companion object {
        private const val TAG = "RepoViewModel"
    }


}