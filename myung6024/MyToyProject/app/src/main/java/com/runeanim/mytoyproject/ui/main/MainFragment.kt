package com.runeanim.mytoyproject.ui.main

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.MainFragmentBinding
import com.runeanim.mytoyproject.ui.repo.RepoListAdapter
import com.runeanim.mytoyproject.ui.repo.RepoViewModel
import com.runeanim.mytoyproject.util.EventObserver
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment<MainFragmentBinding, RepoViewModel>(R.layout.main_fragment) {

    override val viewModel: RepoViewModel by inject()

    private lateinit var listAdapter: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupFab()
        setupListAdapter()

        viewModel.getClickedRepositoryHistory()
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

    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.search_repo_fab)?.let {
            it.setOnClickListener {
                navigateToSearchRepo()
            }
        }
    }

    private fun navigateToSearchRepo(){
        MainFragmentDirections.actionMainScreenToSearchScreen()
            .also { findNavController().navigate(it) }
    }

    private fun openRepoDetail(repositoryEntity: RepositoryEntity){
        with(repositoryEntity) {
            MainFragmentDirections.actionGlobalDetailScreen(
                fullName,
                ownerName
            ).also { findNavController().navigate(it) }
        }
    }
}
