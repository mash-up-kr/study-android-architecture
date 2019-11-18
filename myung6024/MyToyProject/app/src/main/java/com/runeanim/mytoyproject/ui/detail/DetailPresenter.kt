package com.runeanim.mytoyproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import com.runeanim.mytoyproject.util.SingleLiveEvent
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailPresenter(
    private val repoUrl: String,
    private val userId: String,
    private val getRepositoryInfoUseCase: GetRepositoryInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val detailView: DetailContract.View
) : DetailContract.Presenter {

    private val _dataLoading = MutableLiveData<Boolean>(true)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _repoInfo = SingleLiveEvent<Repository>()
    val repoInfo: LiveData<Repository>
        get() = _repoInfo

    private val _ownerInfo = SingleLiveEvent<Owner>()
    val ownerInfo: LiveData<Owner>
        get() = _ownerInfo

    override fun start() {
        getUserAndRepositoryInfo()
    }

    override fun getUserAndRepositoryInfo() {
        if (repoUrl.isEmpty() || userId.isEmpty())
            return

        showProgressBar()
        wrapEspressoIdlingResource {
            coroutineScope.launch {
                val getRepoJob = async(Dispatchers.IO) { getRepositoryInfoUseCase(repoUrl) }
                val getUserJob = async(Dispatchers.IO) { getUserInfoUseCase(userId) }

                setInfoItems(getRepoJob.await(), getUserJob.await())
                hideProgressBar()
            }
        }
    }

    private fun setInfoItems(repoResult: Result<Repository>, userResult: Result<Owner>) {
        if (repoResult is Success && userResult is Success) {
            _repoInfo.value = repoResult.data
            _ownerInfo.value = userResult.data
        } else {
            detailView.showError()
        }
    }

    private fun hideProgressBar() {
        _dataLoading.value = false
    }

    private fun showProgressBar() {
        _dataLoading.value = true
    }
}