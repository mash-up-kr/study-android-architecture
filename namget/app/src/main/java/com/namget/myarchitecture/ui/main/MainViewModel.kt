package com.namget.myarchitecture.ui.main

import androidx.databinding.ObservableArrayList
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseViewModel

/**
 * Created by Namget on 2019.12.01.
 */
class MainViewModel(
    private val repoRepository: RepoRepository,
    private val toastItemCallback: (Int) -> Unit
) : BaseViewModel() {
    val list = ObservableArrayList<RepoItemEntity>()

    fun selectRepoData() {
        isLoading.set(true)
        disposable += repoRepository.selectRepoData()
            .subscribe({
                e(TAG, it.toString())
                list.addAll(it)
                isLoading.set(false)
            }, {
                toastItemCallback(R.string.error)
                e(TAG, "selectRepoData", it)
                isLoading.set(false)
            })
    }


    companion object {
        private const val TAG = "MainViewModel"
    }

}