package com.runeanim.mytoyproject.ui.main

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.domain.GetRepositoriesUseCase
import com.runeanim.mytoyproject.domain.RemoveAllRepositoriesUseCase
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val removeAllRepositoriesUseCase: RemoveAllRepositoriesUseCase,
    private val mainView: MainContract.View
) : MainContract.Presenter {

    override fun start() {
        getRepositoryHistory()
    }

    override fun getRepositoryHistory() {
        wrapEspressoIdlingResource {
            coroutineScope.launch {
                val result = getRepositoriesUseCase()
                if (result is Result.Success) {
                    mainView.showRepositoryHistory(result.data)
                }
            }
        }
    }

    override fun removeAllRepositoryHistory() {
        wrapEspressoIdlingResource {
            coroutineScope.launch {
                launch(Dispatchers.IO) {
                    removeAllRepositoriesUseCase()
                }.join()
                mainView.showRepositoryHistory(emptyList())
            }
        }
    }
}