package com.namget.myarchitecture.ui.search

import androidx.databinding.ObservableArrayList
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseViewModel

/**
 * Created by Namget on 2019.12.01.
 */
class SearchViewModel(
    private val repoRepository: RepoRepository,
    private val toastItemCallback: (Int) -> Unit,
    private val keyboardCallback: (Boolean) -> Unit
) : BaseViewModel() {
    val list = ObservableArrayList<RepoListResponse.RepoItem?>()

    fun requestRepoList(query: String) {
        keyboardCallback(false)
        isLoading.set(true)
        disposable += repoRepository.getRepositoryList(query)
            .subscribe({
                list.clear()
                list.addAll(it.items ?: listOf())
                isLoading.set(false)
            }, {
                toastItemCallback(R.string.error)
                isLoading.set(false)
            })
    }


    fun insertRepoData(repoItem: RepoListResponse.RepoItem) {
        disposable += repoRepository.insertRepoData(repoItem.toRepoEntity())
            .subscribe {
                e(TAG, "inserted")
            }
    }


    companion object {
        private const val TAG = "SearchViewModel"
    }
}