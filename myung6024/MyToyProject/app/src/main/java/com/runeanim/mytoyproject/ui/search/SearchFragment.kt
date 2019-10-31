package com.runeanim.mytoyproject.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.runeanim.mytoyproject.base.BaseRepoListFragment
import com.runeanim.mytoyproject.Constants
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.RepoListAdapter
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class SearchFragment : BaseRepoListFragment() {

    private val searchRepositoriesUseCase: SearchRepositoriesUseCase by inject()
    private val saveRepositoriesUseCase: SaveRepositoryUseCase by inject()

    private lateinit var listAdapter: RepoListAdapter
    private lateinit var viewDataBinding: SearchFragmentBinding

    private val saveJob = CoroutineScope(Dispatchers.IO)
    private val searchJob = CoroutineScope(Dispatchers.Main)

    private val _items = MutableLiveData<List<RepositoryEntity>>().apply { value = emptyList() }
    val items: LiveData<List<RepositoryEntity>> = _items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            SearchFragmentBinding.inflate(inflater, container, false).apply {
                fragment = this@SearchFragment
            }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob.cancel()
        saveJob.cancel()
    }

    private fun setupListAdapter() {
        listAdapter = RepoListAdapter(this)
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    override fun onClickRepositoryItem(repositoryEntity: RepositoryEntity){
        saveRepository(repositoryEntity)
        moveScreenToDetailFragment(repositoryEntity)
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
        return RepositoryEntity(repository.id, repository.fullName, repository.owner.login, repository.language, repository.owner.avatarUrl)
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
