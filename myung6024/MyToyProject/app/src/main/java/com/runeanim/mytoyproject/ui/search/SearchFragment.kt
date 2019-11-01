package com.runeanim.mytoyproject.ui.search

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.runeanim.mytoyproject.ui.RepoItemClickListener
import com.runeanim.mytoyproject.Constants
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.ui.RepoListAdapter
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    private val searchRepositoriesUseCase: SearchRepositoriesUseCase by inject()
    private val saveRepositoriesUseCase: SaveRepositoryUseCase by inject()

    private lateinit var listAdapter: RepoListAdapter

    private val saveJob = CoroutineScope(Dispatchers.IO)
    private val searchJob = CoroutineScope(Dispatchers.Main)

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

    override fun onDestroy() {
        super.onDestroy()
        searchJob.cancel()
        saveJob.cancel()
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
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_USER_NAME, repositoryEntity.ownerName)
        bundle.putString(Constants.EXTRA_REPOSITORY_URL, repositoryEntity.fullName)
        Navigation.findNavController(viewDataBinding.root)
            .navigate(R.id.action_search_screen_to_detail_screen, bundle)
    }

    private fun saveRepository(repositoryEntity: RepositoryEntity) {
        saveJob.launch {
            saveRepositoriesUseCase(repositoryEntity)
        }
    }

    private fun mapRepositoryToRepositoryEntity(repository: Repository): RepositoryEntity{
        return RepositoryEntity(repository.fullName, repository.owner.login, repository.language, repository.owner.avatarUrl)
    }

    val searchRepositoryByKeyWord = fun(searchKeyWord: String) {
        searchJob.launch {
            val result = searchRepositoriesUseCase(searchKeyWord)
            if (result is Success) {
                _items.value = result.data.repositories.map { mapRepositoryToRepositoryEntity(it) }
            }
        }
    }
}
