package com.namget.myarchitecture.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.api.RetrofitBuilder
import com.namget.myarchitecture.data.api.response.RepoListResponse
import com.namget.myarchitecture.ext.d
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ext.withScheduler
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.repo.RepoActivity
import com.namget.myarchitecture.util.URL_REPO_DATA
import com.namget.myarchitecture.util.URL_USER_DATA
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by Namget on 2019.10.22.
 */

/**
 *  Class layout
 *  Property declarations and initializer blocks
 *  Secondary constructors
 *  Method declarations
 *  Companion object
 */

class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private lateinit var menuSearch: MenuItem
    private lateinit var searchView: SearchView
    private val diffUtilCallback =
        object : DiffUtil.ItemCallback<RepoListResponse.RepoItem>() {
            override fun areItemsTheSame(
                oldItem: RepoListResponse.RepoItem,
                newItem: RepoListResponse.RepoItem
            ): Boolean {
                return oldItem.fullName == newItem.fullName
            }

            override fun areContentsTheSame(
                oldItem: RepoListResponse.RepoItem,
                newItem: RepoListResponse.RepoItem
            ): Boolean {
                return oldItem.fullName == newItem.fullName &&
                        oldItem.language == newItem.language &&
                        oldItem.owner == newItem.owner
            }
        }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(diffUtilCallback) {
            d(TAG, "selectedItem ${searchAdapter.getAdapterItem(it)}")
            //db 저장도 해야함

            //detail
            startActivity(Intent(this, RepoActivity::class.java).apply {
                putExtra(URL_REPO_DATA, searchAdapter.getAdapterItem(it).url)
                putExtra(URL_USER_DATA, searchAdapter.getAdapterItem(it).owner.userUrl)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    private fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        searchRecylcerView.apply {
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuSearch = menu.findItem(R.id.menu_search)
        searchView = menuSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    showDialog()
                    requestRepoList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        menuSearch.expandActionView()
        return true
    }


    private fun requestRepoList(query: String) {
        disposable += RetrofitBuilder.createApiService()
            .getRepositoryList(query)
            .withScheduler()
            .subscribe({
                searchAdapter.submitList(it.items)
                hideDialog()
            }, {
                hideDialog()
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            menuSearch.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}