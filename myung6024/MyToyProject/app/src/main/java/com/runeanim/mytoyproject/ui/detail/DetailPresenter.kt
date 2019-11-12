package com.runeanim.mytoyproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailPresenter(
    private val repoUrl: String,
    private val userId: String,
    private val coroutineScope: CoroutineScope,
    private val getRepositoryInfoUseCase: GetRepositoryInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val detailView: DetailContract.View
) : DetailContract.Presenter {

    private val _dataLoading = MutableLiveData<Boolean>(true)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    override fun start() {
        getUserAndRepositoryInfo()
    }

    override fun getUserAndRepositoryInfo() {
        if (repoUrl.isEmpty() || userId.isEmpty())
            return

        showProgressBar()
        coroutineScope.launch {
            val getRepoJob = async(Dispatchers.IO) { getRepositoryInfoUseCase(repoUrl) }
            val getUserJob = async(Dispatchers.IO) { getUserInfoUseCase(userId) }

            detailView.setDataBindingItems(getRepoJob.await(), getUserJob.await())
            hideProgressBar()
        }
    }

    private fun hideProgressBar() {
        _dataLoading.value = false
    }

    private fun showProgressBar() {
        _dataLoading.value = true
    }
}