package com.runeanim.mytoyproject.ui.main

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.runeanim.mytoyproject.ui.RepoItemClickListener
import com.runeanim.mytoyproject.Constants
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.databinding.MainFragmentBinding
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.domain.GetRepositoriesUseCase
import com.runeanim.mytoyproject.ui.RepoListAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment<MainFragmentBinding>(R.layout.main_fragment) {
    private val getRepositoriesUseCase: GetRepositoriesUseCase by inject()

    private lateinit var listAdapter: RepoListAdapter

    private val _items = MutableLiveData<List<RepositoryEntity>>().apply { value = emptyList() }
    val items: LiveData<List<RepositoryEntity>> = _items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            fragment = this@MainFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        getClickedRepositories()
    }

    private fun getClickedRepositories() {
        coroutineScope.launch {
            val result = getRepositoriesUseCase()
            if (result is Success) {
                _items.value = result.data
            }
        }
    }

    private fun setupListAdapter() {
        listAdapter = RepoListAdapter(object :
            RepoItemClickListener {
            override fun onClickRepositoryItem(repositoryEntity: RepositoryEntity) {
                moveScreenToDetailFragment(repositoryEntity)
            }
        })
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    private fun moveScreenToDetailFragment(repositoryEntity: RepositoryEntity) {
        val bundle = with(repositoryEntity) {
            bundleOf(
                Constants.EXTRA_USER_NAME to ownerName,
                Constants.EXTRA_REPOSITORY_URL to fullName
            )
        }
        Navigation.findNavController(viewDataBinding.root)
            .navigate(R.id.action_global_detail_screen, bundle)
    }

    fun moveScreenToSearchFragment() {
        Navigation.findNavController(viewDataBinding.root)
            .navigate(R.id.action_main_screen_to_search_screen)
    }
}
