package com.tistory.mashuparchitecture.presentation.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.tistory.blackjin.domain.interactor.usecases.GetReposUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.di.ResourcesProvider
import com.tistory.mashuparchitecture.model.mapToPresentation
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var menuSearch: MenuItem

    private lateinit var searchView: SearchView

    private val searchAdapter by lazy { SearchAdapter() }

    private val getRepoUsecase: GetReposUsecase by inject()

    private val resourceProvider: ResourcesProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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
                    updateTitle(query)
                    hideSoftKeyboard()
                    collapseSearchView()
                    searchRepository(query)
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

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    private fun searchRepository(query: String) {

        getRepoUsecase.get(query)
            .map { it.mapToPresentation(resourceProvider) }
            .doOnSubscribe {
                clearResults()
                hideError()
                showProgress()
            }
            .doOnSuccess {
                hideProgress()
            }
            .doOnError {
                hideProgress()
            }
            .subscribe(
                {
                    searchAdapter.setItems(it)

                    if (it.isEmpty()) {
                        showError(getString(R.string.no_search_result))
                    }

                }, ::handleException
            ).also {
                compositeDisposable.add(it)
            }
    }

    private fun handleException(throwable: Throwable) {
        showError(throwable.message)
    }

    private fun updateTitle(query: String) {
        supportActionBar?.run { subtitle = query }
    }

    private fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(searchView.windowToken, 0)
        }
    }

    private fun collapseSearchView() {
        menuSearch.collapseActionView()
    }

    private fun clearResults() {
        searchAdapter.clearItems()
    }

    private fun showProgress() {
        pbActivitySearch.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pbActivitySearch.visibility = View.GONE
    }

    private fun showError(message: String?) {
        with(tvActivitySearchMessage) {
            text = message ?: "Unexpected error."
            visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        with(tvActivitySearchMessage) {
            text = ""
            visibility = View.GONE
        }
    }
}
