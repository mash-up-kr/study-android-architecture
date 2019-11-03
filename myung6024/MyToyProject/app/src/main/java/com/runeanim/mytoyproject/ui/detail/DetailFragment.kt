package com.runeanim.mytoyproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.runeanim.mytoyproject.Constants
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.databinding.DetailFragmentBinding
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {
    private val getRepositoryInfoUseCase: GetRepositoryInfoUseCase by inject()
    private val getUserInfoUseCase: GetUserInfoUseCase by inject()

    private val _dataLoading = MutableLiveData<Boolean>(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val getDetailJob = CoroutineScope(Dispatchers.Main)

    private lateinit var viewDataBinding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        viewDataBinding = DetailFragmentBinding.bind(view).apply {
            fragment = this@DetailFragment
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        arguments?.run {
            getUserAndRepositoryInfo(
                getString(Constants.EXTRA_REPOSITORY_URL) ?: "",
                getString(Constants.EXTRA_USER_NAME) ?: ""
            )
        }

        return view
    }

    override fun onDestroy() {
        getDetailJob.cancel()
        super.onDestroy()
    }

    private fun getUserAndRepositoryInfo(repoUrl: String, userId: String) {
        if (repoUrl.isEmpty() || userId.isEmpty())
            return

        _dataLoading.value = true
        getDetailJob.launch {
            val repoInfoResult =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    getRepositoryInfoUseCase(
                        repoUrl
                    )
                }
            val userInfoResult =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    getUserInfoUseCase(
                        userId
                    )
                }
            if (repoInfoResult is Success && userInfoResult is Success) {
                viewDataBinding.repo = repoInfoResult.data
                viewDataBinding.owner = userInfoResult.data
            } else {
                showError()
            }
            _dataLoading.value = false
        }
    }

    private fun showError() {
        with(tvActivityRepositoryMessage) {
            text = "Unexpected error."
            visibility = View.VISIBLE
        }
    }
}