package com.example.myapplication.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.SearchRepo
import com.example.myapplication.Network.ApiController
import com.example.myapplication.Network.Get.SearchRepoResponse
import com.example.myapplication.Network.NetworkService
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    lateinit var searchItem : MenuItem
    lateinit var searchView : SearchView

    val networkService : NetworkService by lazy {
        ApiController.instance.networkService
    }
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
            override fun onQueryTextSubmit(query: String?): Boolean {
                configRecyclerView()
                getSearchRepoResponse(query!!)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                return false
            }
        })
        return true
    }

    fun getSearchRepoResponse(query : String){
        hideError()
        showProgress()

        val getSearchRepoResponse = networkService.getSearchRepoResponse(query)

        getSearchRepoResponse.enqueue(object : Callback<SearchRepoResponse>{
            override fun onFailure(call: Call<SearchRepoResponse>, t: Throwable) {
                showError()
            }

            override fun onResponse(call: Call<SearchRepoResponse>, response: Response<SearchRepoResponse>) {
                hideProgress()
                if(response.isSuccessful){
                    response.body()!!.items.let {
                        hideError()
                        searchRecyclerviewAdapter.datalist = it!!
                        searchRecyclerviewAdapter.notifyDataSetChanged()
                    }
                    if(response.body()!!.items!!.size == 0){
                        showError()
                    }
                }
            }
        })
    }

    fun configRecyclerView(){
        val dataList : ArrayList<SearchRepo> = ArrayList()

        searchRecyclerviewAdapter = SearchRecyclerviewAdapter(this,dataList)
        rv_search_list_activity_search.adapter = searchRecyclerviewAdapter
        rv_search_list_activity_search.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun showProgress() {
       pb_activity_search.visibility = View.VISIBLE
    }

    private fun hideProgress() {
       pb_activity_search.visibility = View.GONE
    }

    private fun showError(){
        rv_search_list_activity_search.visibility = View.GONE
        tv_no_result_activity_search.visibility = View.VISIBLE
    }

    private fun hideError(){
        tv_no_result_activity_search.visibility = View.GONE
    }
}
