package com.tistory.mashuparchitecture.presentation.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.tistory.blackjin.domain.interactor.usecases.AddRepoHistoryUsecase
import com.tistory.blackjin.domain.interactor.usecases.GetReposUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.util.ResourcesProvider
import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.model.mapToHistoryDomain
import com.tistory.mashuparchitecture.presentation.repo.RepositoryActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity(), SearchContract.View {

    private lateinit var menuSearch: MenuItem

    private lateinit var searchView: SearchView

    override lateinit var presenter: SearchContract.Presenter

    private val getRepoUsecase: GetReposUsecase by inject()

    private val addRepoUsecase: AddRepoHistoryUsecase by inject()

    private val resourceProvider: ResourcesProvider by inject()

    private val searchAdapter by lazy { SearchAdapter(itemListener) }

    private val itemListener: (item: RepoItem) -> Unit =
        { item ->

            addRepoUsecase.get(item.mapToHistoryDomain())

            RepositoryActivity.startRepositoryActivity(
                this,
                item.owner.ownerName,
                item.repoName
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter = SearchPresenter(this, getRepoUsecase, resourceProvider)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvActivitySearchList.adapter = searchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search, menu)
        menuSearch = menu.findItem(R.id.menu_activity_search_query)

        searchView = (menuSearch.actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter.searchRepository(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        menuSearch.expandActionView()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_activity_search_query == item.itemId) {
            item.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showTopTitle(title: String) {
        supportActionBar?.run { subtitle = title }
    }

    override fun showCollapseSearchView() {
        menuSearch.collapseActionView()
    }

    override fun showRepos(items: List<RepoItem>) {
        searchAdapter.setItems(items)
    }

    override fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(searchView.windowToken, 0)
        }
    }

    override fun showProgress() {
        pbActivitySearch.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbActivitySearch.visibility = View.GONE
    }

    override fun showError(message: String?) {
        with(tvActivitySearchMessage) {
            text = message ?: "Unexpected error."
            visibility = View.VISIBLE
        }
    }

    override fun hideError() {
        with(tvActivitySearchMessage) {
            text = ""
            visibility = View.GONE
        }
    }
}
