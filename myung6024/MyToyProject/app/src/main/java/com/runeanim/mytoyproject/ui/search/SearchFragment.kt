package com.runeanim.mytoyproject.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import com.runeanim.mytoyproject.ui.repo.RepoListAdapter
import com.runeanim.mytoyproject.ui.repo.RepoViewModel
import com.runeanim.mytoyproject.util.EventObserver
import org.koin.android.ext.android.inject


class SearchFragment :
    BaseFragment<SearchFragmentBinding, RepoViewModel>(R.layout.search_fragment) {

    private lateinit var listAdapter: RepoListAdapter

    override val viewModel: RepoViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupListAdapter()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyBoard()
    }

    private fun setupListAdapter() {
        listAdapter = RepoListAdapter(viewModel)
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    private fun setupNavigation() {
        viewModel.openRepoEvent.observe(this, EventObserver {
            openRepoDetail(it)
        })
    }

    private fun hideSoftKeyBoard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewDataBinding.searchView.windowToken, 0)
    }

    private fun openRepoDetail(repositoryEntity: RepositoryEntity){
        with(repositoryEntity) {
            SearchFragmentDirections.actionGlobalDetailScreen(
                fullName,
                ownerName
            ).also { findNavController().navigate(it) }
        }
    }
}
