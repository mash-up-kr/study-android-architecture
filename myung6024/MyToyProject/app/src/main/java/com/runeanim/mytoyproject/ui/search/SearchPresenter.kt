package com.runeanim.mytoyproject.ui.search

import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.data.source.remote.response.mapToPresentation
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchPresenter(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val saveRepositoriesUseCase: SaveRepositoryUseCase,
    private val searchView: SearchContract.View
) : SearchContract.Presenter {

    override fun start() {
    }

    override val searchRepositoryByKeyWord: (String) -> Unit
        get() = fun(searchKeyWord: String) {
            searchView.hideResultMessage()
            searchView.showProgressBar()
            wrapEspressoIdlingResource {
                coroutineScope.launch {
                    val result = searchRepositoriesUseCase(searchKeyWord)
                    if (result is Result.Success) {
                        with(result.data.mapToPresentation()) {
                            searchView.showSearchResult(this)
                            if (size == 0) {
                                searchView.showResultMessage()
                            } else {
                                searchView.hideResultMessage()
                            }
                        }
                    } else {
                        searchView.showSearchResult(emptyList())
                        searchView.showResultMessage()
                    }
                    searchView.hideProgressBar()
                }
            }
        }

    override fun saveRepositoryClickHistory(repositoryEntity: RepositoryEntity) {
        wrapEspressoIdlingResource {
            coroutineScope.launch(Dispatchers.IO) {
                saveRepositoriesUseCase(repositoryEntity.apply {
                    order = System.currentTimeMillis()
                })
            }
        }
    }


}