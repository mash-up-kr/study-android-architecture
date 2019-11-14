package com.runeanim.mytoyproject.ui.search

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.runeanim.mytoyproject.ui.RepoItemClickListener
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.ui.RepoListAdapter
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import org.koin.android.ext.android.inject
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import org.koin.core.parameter.parametersOf


class SearchFragment : BaseFragment<SearchFragmentBinding, SearchPresenter>(R.layout.search_fragment),
    SearchContract.View {

    private lateinit var listAdapter: RepoListAdapter

    override val presenter: SearchPresenter by inject {
        parametersOf(
            this as SearchContract.View
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            presenter = this@SearchFragment.presenter
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun setupListAdapter() {
        listAdapter = RepoListAdapter(object :
            RepoItemClickListener {
            override fun onClickRepositoryItem(repositoryEntity: RepositoryEntity) {
                presenter.onClickRepositoryItem(repositoryEntity)
            }
        })
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    override fun hideSoftKeyBoard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewDataBinding.searchView.windowToken, 0)
    }

    override fun moveScreenToDetailFragment(repositoryEntity: RepositoryEntity) {
        with(repositoryEntity) {
            SearchFragmentDirections.actionGlobalDetailScreen(
                fullName,
                ownerName
            ).also { findNavController().navigate(it) }
        }
    }
}
