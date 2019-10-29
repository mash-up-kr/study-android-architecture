package com.runeanim.mytoyproject.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.runeanim.mytoyproject.Constants
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.databinding.SearchFragmentBinding
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private val searchRepositoriesUseCase: SearchRepositoriesUseCase by inject()
    private lateinit var listAdapter: SearchAdapter
    private lateinit var viewDataBinding: SearchFragmentBinding

    private val searchJob = CoroutineScope(Dispatchers.Main)

    private val _items = MutableLiveData<List<Repository>>().apply { value = emptyList() }
    val items: LiveData<List<Repository>> = _items

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
    }

    private fun setupListAdapter() {
        listAdapter = SearchAdapter(this)
        viewDataBinding.rvRepository.adapter = listAdapter
    }

    fun moveScreenToDetailFragment(repository: Repository) {
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_USER_NAME, repository.owner.login)
        bundle.putString(Constants.EXTRA_REPOSITORY_URL, repository.fullName)
        Navigation.findNavController(viewDataBinding.root)
            .navigate(R.id.action_search_screen_to_detail_screen, bundle)
    }

    val searchRepositoryByKeyWord = fun(searchKeyWord: String) {
        searchJob.launch {
            val result = searchRepositoriesUseCase(searchKeyWord)
            if (result is Success) {
                _items.value = result.data.repositories
            }
        }
    }
}
