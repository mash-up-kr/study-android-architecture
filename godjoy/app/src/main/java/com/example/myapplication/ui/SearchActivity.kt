package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.network.model.SearchRepo
import com.example.myapplication.network.model.SearchRepoResponse
import com.example.myapplication.network.SearchRetrofit
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), NetworkException {
    lateinit var searchItem : MenuItem
    lateinit var searchView : SearchView

    lateinit var searchRecyclerviewAdapter : SearchRecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchItem = menu.findItem(R.id.app_bar_search)
        searchView = searchItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "Search.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                configRecyclerView()
                getSearchRepoResponse(query)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                return false
            }
        })
        return true
    }

    fun getSearchRepoResponse(query : String){
        hideError(tvNoResultActivitySearch)
        showProgress(pbActivitySearch)

        val getSearchRepoResponse = SearchRetrofit.getService().getSearchRepoResponse(query)

        getSearchRepoResponse.enqueue(object : Callback<SearchRepoResponse>{
            override fun onFailure(call: Call<SearchRepoResponse>, t: Throwable) {
                showError(tvNoResultActivitySearch)
            }

            override fun onResponse(call: Call<SearchRepoResponse>, response: Response<SearchRepoResponse>) {
                hideProgress(pbActivitySearch)
                if(response.isSuccessful){
                    hideError(tvNoResultActivitySearch)
                    response.body()!!.items?.let {repo->
                        with(searchRecyclerviewAdapter){
                            datalist = repo
                            notifyDataSetChanged()
                        }
                    }
                    if(response.body()!!.items!!.size == 0){
                        showError(tvNoResultActivitySearch)
                    }
                }else{
                    showError(tvNoResultActivitySearch)
                }
            }
        })
    }

    fun configRecyclerView(){
        val dataList : ArrayList<SearchRepo> = ArrayList()

        searchRecyclerviewAdapter = SearchRecyclerviewAdapter(this,dataList)
        rvSearchListActivitySearch.adapter = searchRecyclerviewAdapter
        rvSearchListActivitySearch.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}
