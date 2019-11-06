package com.runeanim.mytoyproject.ui.search

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.runeanim.mytoyproject.ui.RepoItemClickListener
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.ui.RepoListAdapter
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.data.source.remote.response.mapToPresentation
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    private val searchRepositoriesUseCase: SearchRepositoriesUseCase by inject()
    private val saveRepositoriesUseCase: SaveRepositoryUseCase by inject()

    private lateinit var listAdapter: RepoListAdapter

    private val _items = MutableLiveData<List<RepositoryEntity>>().apply { value = emptyList() }
    val items: LiveData<List<RepositoryEntity>> = _items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            fragment = this@SearchFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        listAdapter = RepoListAdapter(object :
            RepoItemClickListener {
            override fun onClickRepositoryItem(repositoryEntity: RepositoryEntity) {
                saveRepository(repositoryEntity)
                moveScreenToDetailFragment(repositoryEntity)
            }
        })
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    private fun moveScreenToDetailFragment(repositoryEntity: RepositoryEntity) {
        with(repositoryEntity) {
            SearchFragmentDirections.actionGlobalDetailScreen(
                fullName,
                ownerName
            ).also { findNavController().navigate(it) }
        }
    }

    private fun saveRepository(repositoryEntity: RepositoryEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            saveRepositoriesUseCase(repositoryEntity.apply { order = System.currentTimeMillis() })
        }
    }

    val searchRepositoryByKeyWord = fun(searchKeyWord: String) {
        coroutineScope.launch {
            val result = searchRepositoriesUseCase(searchKeyWord)
            if (result is Success) {
                _items.value = result.data.mapToPresentation()
            }
        }
    }
}
