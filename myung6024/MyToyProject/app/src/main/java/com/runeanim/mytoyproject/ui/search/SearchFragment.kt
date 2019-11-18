package com.runeanim.mytoyproject.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import com.runeanim.mytoyproject.ui.RepoItemClickListener
import com.runeanim.mytoyproject.ui.RepoListAdapter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SearchFragment :
    BaseFragment<SearchFragmentBinding, SearchPresenter>(R.layout.search_fragment),
    SearchContract.View, RepoItemClickListener {

    private lateinit var listAdapter: RepoListAdapter

    override val presenter: SearchPresenter by inject {
        parametersOf(
            this as SearchContract.View
        )
    }

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _items = MutableLiveData<List<RepositoryEntity>>().apply { value = emptyList() }
    val items: LiveData<List<RepositoryEntity>>
        get() = _items

    private val _messageResId = MutableLiveData<@StringRes Int?>(null)
    val messageResId: LiveData<Int?>
        get() = _messageResId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            view = this@SearchFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyBoard()
    }

    private fun setupListAdapter() {
        listAdapter = RepoListAdapter(this)
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    fun onClickSearchButton(): (String) -> Unit {
        return presenter.searchRepositoryByKeyWord
    }

    override fun showSearchResult(result: List<RepositoryEntity>) {
        _items.value = result
    }

    private fun hideSoftKeyBoard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewDataBinding.searchView.windowToken, 0)
    }

    override fun onClickRepositoryItem(repositoryEntity: RepositoryEntity) {
        with(repositoryEntity) {
            presenter.saveRepositoryClickHistory(this)
            SearchFragmentDirections.actionGlobalDetailScreen(
                fullName,
                ownerName
            ).also { findNavController().navigate(it) }
        }
    }

    override fun hideResultMessage() {
        _messageResId.value = null
    }

    override fun showResultMessage() {
        _messageResId.value = R.string.no_search_result
    }

    override fun hideProgressBar() {
        _dataLoading.value = false
    }

    override fun showProgressBar() {
        _dataLoading.value = true
    }
}
