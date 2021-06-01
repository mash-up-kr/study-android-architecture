package com.runeanim.mytoyproject.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repoUrl: String,
    private val userId: String,
    private val getRepositoryInfoUseCase: GetRepositoryInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorText = MutableLiveData<Int>()
    val errorText: LiveData<Int> = _errorText

    private val _ownerInfo = MutableLiveData<Owner>()
    val ownerInfo: LiveData<Owner> = _ownerInfo

    private val _repoInfo = MutableLiveData<Repository>()
    val repoInfo: LiveData<Repository> = _repoInfo

    fun start() {
        getUserAndRepositoryInfo()
    }

    @VisibleForTesting
    fun getUserAndRepositoryInfo() {
        if (repoUrl.isEmpty() || userId.isEmpty())
            return

        showProgressBar()
        wrapEspressoIdlingResource {
            viewModelScope.launch {
                val getRepoJob = async(Dispatchers.IO) { getRepositoryInfoUseCase(repoUrl) }
                val getUserJob = async(Dispatchers.IO) { getUserInfoUseCase(userId) }

                setInfoItems(getRepoJob.await(), getUserJob.await())
                hideProgressBar()
            }
        }
    }

    private fun setInfoItems(repoResult: Result<Repository>, userResult: Result<Owner>) {
        if (repoResult is Success && userResult is Success) {
            _ownerInfo.value = userResult.data
            _repoInfo.value = repoResult.data
        } else {
            _errorText.value = R.string.unknown
        }
    }

    private fun showProgressBar() {
        _isLoading.value = true
    }

    private fun hideProgressBar() {
        _isLoading.value = false
    }
}
