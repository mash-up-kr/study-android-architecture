package com.runeanim.mytoyproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.domain.GetRepositoriesUseCase
import com.runeanim.mytoyproject.domain.RemoveAllRepositoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val removeAllRepositoriesUseCase: RemoveAllRepositoriesUseCase,
    private val mainView: MainContract.View
) : MainContract.Presenter {

    private val _items = MutableLiveData<List<RepositoryEntity>>(emptyList())
    val items: LiveData<List<RepositoryEntity>>
        get() = _items

    override fun start() {
        getClickedRepositories()
        mainView.setupListAdapter()
    }

    override fun onClickListItem(repositoryEntity: RepositoryEntity) {
        mainView.moveScreenToDetailFragment(repositoryEntity)
    }

    override fun getClickedRepositories() {
        coroutineScope.launch {
            val result = getRepositoriesUseCase()
            if (result is Result.Success) {
                _items.value = result.data
            }
        }
    }

    override fun onClickRemoveAllFloatingButton() {
        coroutineScope.launch {
            launch(Dispatchers.IO) {
                removeAllRepositoriesUseCase()
            }.join()
            _items.value = emptyList()
        }
    }

    override fun onClickSearchFloatingButton() {
        mainView.moveScreenToSearchFragment()
    }
}