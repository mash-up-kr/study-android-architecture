package com.runeanim.mytoyproject.ui.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.MainFragmentBinding
import com.runeanim.mytoyproject.ui.RepoItemClickListener
import com.runeanim.mytoyproject.ui.RepoListAdapter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment<MainFragmentBinding, MainPresenter>(R.layout.main_fragment),
    MainContract.View {

    override val presenter: MainPresenter by inject {
        parametersOf(
            this as MainContract.View
        )
    }

    private lateinit var listAdapter: RepoListAdapter

    private val _items = MutableLiveData<List<RepositoryEntity>>(emptyList())
    val items: LiveData<List<RepositoryEntity>>
        get() = _items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            view = this@MainFragment
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
                onClickListItem(repositoryEntity)
            }
        })
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    override fun showRepositoryHistory(result: List<RepositoryEntity>) {
        _items.value = result
    }

    private fun onClickListItem(repositoryEntity: RepositoryEntity) {
        with(repositoryEntity) {
            MainFragmentDirections.actionGlobalDetailScreen(
                fullName,
                ownerName
            ).also { findNavController().navigate(it) }
        }
    }

    fun onClickRemoveAllFloatingButton() {
        presenter.removeAllRepositoryHistory()
    }

    fun onClickSearchFloatingButton() {
        MainFragmentDirections.actionMainScreenToSearchScreen()
            .also { findNavController().navigate(it) }
    }
}
