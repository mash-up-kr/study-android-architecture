package com.namget.myarchitecture.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.ext.hideKeyboard
import com.namget.myarchitecture.ext.setVisible
import com.namget.myarchitecture.ext.showToast
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

class SearchActivity : BaseActivity(), SearchContract.View {
    private lateinit var menuSearch: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var searchPresenter: SearchPresenter


    private val diffUtilCallback =
        object : DiffUtil.ItemCallback<RepoListResponse.RepoItem>() {
            override fun areItemsTheSame(
                oldItem: RepoListResponse.RepoItem,
                newItem: RepoListResponse.RepoItem
            ): Boolean {
                return oldItem.nodeId == newItem.nodeId
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
            //db
            searchPresenter.insertRepoData(searchAdapter.getAdapterItem(it))
            //detail
            startRepoActivity(
                searchAdapter.getAdapterItem(it).fullName,
                searchAdapter.getAdapterItem(it).owner.login
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    private fun init() {
        initRecyclerView()
        setPresenter()
    }

    private fun initRecyclerView() {
        searchRecylcerView.apply {
            setHasFixedSize(true)
            adapter = searchAdapter
        }

    }

    override fun setPresenter() {
        this.searchPresenter = SearchPresenter(repoRepository, this)
    }

    override fun showDialog() {
        progressBar.setVisible(true)
        searchRecylcerView.setVisible(false)
    }

    override fun hideDialog() {
        progressBar.setVisible(false)
        searchRecylcerView.setVisible(true)
    }

    override fun onDestroy() {
        searchPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun submitList(list: List<RepoListResponse.RepoItem>?) = searchAdapter.submitList(list)
    override fun makeToast(resId: Int) = showToast(resId)

    private fun startRepoActivity(fullName: String, userId: String) {
        startActivity(Intent(this, RepoActivity::class.java).apply {
            putExtra(URL_REPO_DATA, fullName)
            putExtra(URL_USER_DATA, userId)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuSearch = menu.findItem(R.id.menu_search)
        searchView = menuSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    hideKeyboard()
                    showDialog()
                    searchPresenter.requestRepoList(query)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            menuSearch.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}