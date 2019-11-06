package com.runeanim.mytoyproject.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.runeanim.mytoyproject.Constants
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.databinding.DetailFragmentBinding
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {
    private val getRepositoryInfoUseCase: GetRepositoryInfoUseCase by inject()
    private val getUserInfoUseCase: GetUserInfoUseCase by inject()

    private val _dataLoading = MutableLiveData<Boolean>(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            fragment = this@DetailFragment
        }

        arguments?.run {
            getUserAndRepositoryInfo(
                getString(Constants.EXTRA_REPOSITORY_URL) ?: "",
                getString(Constants.EXTRA_USER_NAME) ?: ""
            )
        }
    }

    private fun getUserAndRepositoryInfo(repoUrl: String, userId: String) {
        if (repoUrl.isEmpty() || userId.isEmpty())
            return

        _dataLoading.value = true
        coroutineScope.launch {
            val getRepoJob = async(Dispatchers.IO) { getRepositoryInfoUseCase(repoUrl) }
            val getUserJob = async(Dispatchers.IO) { getUserInfoUseCase(userId) }

            setDataBindingItems(getRepoJob.await(), getUserJob.await())
            _dataLoading.value = false
        }
    }

    private fun setDataBindingItems(repoResult: Result<Repository>, userResult: Result<Owner>) {
        if (repoResult is Success && userResult is Success) {
            viewDataBinding.repo = repoResult.data
            viewDataBinding.owner = userResult.data
        } else {
            showError()
        }
    }

    private fun showError() {
        with(tvActivityRepositoryMessage) {
            text = "Unexpected error."
            visibility = View.VISIBLE
        }
    }
}