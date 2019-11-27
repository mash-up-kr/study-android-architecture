package com.runeanim.mytoyproject.ui.detail

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

class DetailPresenter(
    private val repoUrl: String,
    private val userId: String,
    private val getRepositoryInfoUseCase: GetRepositoryInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val detailView: DetailContract.View
) : DetailContract.Presenter {

    override fun start() {
        getUserAndRepositoryInfo()
    }

    override fun getUserAndRepositoryInfo() {
        if (repoUrl.isEmpty() || userId.isEmpty())
            return

        detailView.showProgressBar()
        wrapEspressoIdlingResource {
            coroutineScope.launch {
                val getRepoJob = async(Dispatchers.IO) { getRepositoryInfoUseCase(repoUrl) }
                val getUserJob = async(Dispatchers.IO) { getUserInfoUseCase(userId) }

                setInfoItems(getRepoJob.await(), getUserJob.await())
                detailView.hideProgressBar()
            }
        }
    }

    private fun setInfoItems(repoResult: Result<Repository>, userResult: Result<Owner>) {
        if (repoResult is Success && userResult is Success) {
            detailView.showRepositoryDetail(repoResult.data, userResult.data)
        } else {
            detailView.showError()
        }
    }
}