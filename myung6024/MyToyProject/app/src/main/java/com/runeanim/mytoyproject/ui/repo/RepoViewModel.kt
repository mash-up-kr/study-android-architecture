package com.runeanim.mytoyproject.ui.repo

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.data.source.remote.response.mapToPresentation
import com.runeanim.mytoyproject.domain.GetRepositoriesUseCase
import com.runeanim.mytoyproject.domain.RemoveAllRepositoriesUseCase
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import com.runeanim.mytoyproject.util.Event
import com.runeanim.mytoyproject.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val saveRepositoriesUseCase: SaveRepositoryUseCase,
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val removeAllRepositoriesUseCase: RemoveAllRepositoriesUseCase
) : ViewModel() {

    private enum class RepoListType {
        SEARCHED,
        CLICKED
    }

    private var repoListType: RepoListType? = null

    private val _items = MutableLiveData<List<RepositoryEntity>>(emptyList())
    val items: LiveData<List<RepositoryEntity>> = _items

    private val _openRepoEvent = MutableLiveData<Event<RepositoryEntity>>()
    val openRepoEvent: LiveData<Event<RepositoryEntity>> = _openRepoEvent

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorTextVisible = MutableLiveData<Boolean>(false)
    val errorTextVisible: LiveData<Boolean> = _errorTextVisible

    val searchRepositoryByKeyWord: (String) -> Unit
        get() = fun(searchKeyWord: String) {
            repoListType = RepoListType.SEARCHED
            showProgressBar()
            hideErrorText()
            wrapEspressoIdlingResource {
                viewModelScope.launch {
                    val result = searchRepositoriesUseCase(searchKeyWord)
                    if (result is Result.Success) {
                        with(result.data.mapToPresentation()) {
                            _items.value = this
                            if (size == 0) showErrorText()
                        }
                    } else {
                        _items.value = emptyList()
                        showErrorText()
                    }
                    hideProgressBar()
                }
            }
        }

    fun getClickedRepositoryHistory() {
        repoListType = RepoListType.CLICKED
        wrapEspressoIdlingResource {
            viewModelScope.launch {
                val result = getRepositoriesUseCase()
                if (result is Result.Success) {
                    _items.value = result.data
                }
            }
        }
    }

    @VisibleForTesting
    fun saveClickedRepositoryHistory(repositoryEntity: RepositoryEntity) {
        wrapEspressoIdlingResource {
            viewModelScope.launch(Dispatchers.IO) {
                saveRepositoriesUseCase(repositoryEntity.apply {
                    order = System.currentTimeMillis()
                })
            }
        }
    }

    fun removeAllRepositoryHistory() {
        wrapEspressoIdlingResource {
            viewModelScope.launch {
                launch(Dispatchers.IO) {
                    removeAllRepositoriesUseCase()
                }.join()
                _items.value = emptyList()
            }
        }
    }

    fun openRepo(repositoryEntity: RepositoryEntity) {
        repoListType?.let {
            if (it == RepoListType.SEARCHED) saveClickedRepositoryHistory(
                repositoryEntity
            )
        }
        _openRepoEvent.value = Event(repositoryEntity)
    }

    private fun showProgressBar() {
        _isLoading.value = true
    }

    private fun hideProgressBar() {
        _isLoading.value = false
    }

    private fun showErrorText() {
        _errorTextVisible.value = true
    }

    private fun hideErrorText() {
        _errorTextVisible.value = false
    }
}
