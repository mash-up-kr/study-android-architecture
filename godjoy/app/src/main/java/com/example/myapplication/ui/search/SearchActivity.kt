package com.example.myapplication.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.SearchRepo
import com.example.myapplication.model.SearchRepoResponse
import com.example.myapplication.network.GithubApiProvider
import com.example.myapplication.R
import com.example.myapplication.ui.NetworkException
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), SearchContract.View {
    private lateinit var searchItem : MenuItem
    private lateinit var searchView : SearchView
    private lateinit var searchRecyclerviewAdapter : SearchRecyclerviewAdapter

    private val presenter by lazy {
        SearchPresenter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchItem = menu.findItem(R.id.app_bar_search)
        searchView = searchItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "Search.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                init()
                presenter.requestSearchData(query)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                return false
            }
        })
        return true
    }


    override fun init() {
        val dataList : ArrayList<SearchRepo> = ArrayList()

        searchRecyclerviewAdapter = SearchRecyclerviewAdapter(this, dataList)
        rvSearchListActivitySearch.adapter = searchRecyclerviewAdapter
        rvSearchListActivitySearch.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun loadSearchData(searchData: SearchRepoResponse?) {
        searchData?.items?.let {repo->
            with(searchRecyclerviewAdapter){
                datalist = repo
                notifyDataSetChanged()
            }
        }
    }

    override fun showError() {
        rvSearchListActivitySearch.visibility = View.GONE
        tvNoResultActivitySearch.visibility = View.VISIBLE
    }

    override fun hideError() {
        tvNoResultActivitySearch.visibility = View.GONE
    }

    override fun showProgress() {
        pbActivitySearch.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbActivitySearch.visibility = View.GONE
    }

}
