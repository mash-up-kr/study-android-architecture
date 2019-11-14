package com.runeanim.mytoyproject.ui.search

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.data.source.remote.response.mapToPresentation
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchPresenter(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val saveRepositoriesUseCase: SaveRepositoryUseCase,
    private val searchView: SearchContract.View
) : SearchContract.Presenter {

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _items = MutableLiveData<List<RepositoryEntity>>().apply { value = emptyList() }
    val items: LiveData<List<RepositoryEntity>>
        get() = _items

    private val _messageResId = MutableLiveData<@StringRes Int?>(null)
    val messageResId: LiveData<Int?>
        get() = _messageResId

    override fun start() {
        searchView.setupListAdapter()
    }

    override fun onPause() {
        searchView.hideSoftKeyBoard()
    }

    override val searchRepositoryByKeyWord: (String) -> Unit
        get() = fun(searchKeyWord: String) {
            hideResultMessage()
            showProgressBar()
            wrapEspressoIdlingResource {
                coroutineScope.launch {
                    val result = searchRepositoriesUseCase(searchKeyWord)
                    if (result is Result.Success) {
                        with(result.data.mapToPresentation()) {
                            _items.value = this
                            if (size == 0) {
                                showResultMessage()
                            } else {
                                hideResultMessage()
                            }
                        }
                    } else {
                        _items.value = emptyList()
                        showResultMessage()
                    }
                    hideProgressBar()
                }
            }
        }

    override fun onClickRepositoryItem(repositoryEntity: RepositoryEntity) {
        saveRepository(repositoryEntity)
        searchView.moveScreenToDetailFragment(repositoryEntity)
    }

    private fun saveRepository(repositoryEntity: RepositoryEntity) {
        wrapEspressoIdlingResource {
            coroutineScope.launch(Dispatchers.IO) {
                saveRepositoriesUseCase(repositoryEntity.apply {
                    order = System.currentTimeMillis()
                })
            }
        }
    }

    private fun hideResultMessage() {
        _messageResId.value = null
    }

    private fun showResultMessage() {
        _messageResId.value = R.string.no_search_result
    }

    private fun hideProgressBar() {
        _dataLoading.value = false
    }

    private fun showProgressBar() {
        _dataLoading.value = true
    }
}