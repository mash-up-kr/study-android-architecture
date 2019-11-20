package miinjung.study.test


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import miinjung.study.test.model.List
import miinjung.study.test.network.TestApplication
import miinjung.study.test.network.ServerInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){

    var api: ServerInterface? = TestApplication.api
    internal var apiCall:Call<List>? = null
    internal var searchAdapter : SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycleview()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.bar_result,menu)

        val menuView = menu.findItem(R.id.search)
        val searchView = menuView.actionView as SearchView

        menuView.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)


        menuView?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                rvSearchList.visibility = View.VISIBLE
                tvText.visibility = View.INVISIBLE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {

                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                rvSearchList.visibility = View.VISIBLE
                tvText.visibility = View.INVISIBLE

                supportActionBar?.setTitle("search")
                supportActionBar?.run{ subtitle = query }
                menuView.collapseActionView()
                searchRepos(query)
                return false
            }
        })

        menuView.expandActionView()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(R.id.search == item.itemId){
            item.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun searchRepos(query: String){
        showProgress()

        apiCall = api?.search(query)
        apiCall?.enqueue(object :Callback<List>{
            override fun onResponse(call: Call<List>, response: Response<List>) {
                var data = response.body()

                hideProgress()

                if(response.isSuccessful && data != null) {
                    if(data.totalCount > 0){
                        data.items.let{
                            searchAdapter!!.setItems(it)
                            searchAdapter!!.notifyDataSetChanged()
                        }
                    }else{
                        hideRecyclerView()
                        showTextView()
                    }
                }
            }

            override fun onFailure(call: Call<List>, t: Throwable) {
                Log.e("errer","error")
            }
        })
    }

    private fun hideRecyclerView(){
        rvSearchList.visibility = View.INVISIBLE
    }

    private fun showTextView(){
        tvText.visibility = View.VISIBLE
    }

    private fun showProgress() {
        pbActivitySearch.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pbActivitySearch.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        apiCall?.run { cancel() }
    }
    private fun initRecycleview(){
        searchAdapter = SearchAdapter(this.applicationContext)

        rvSearchList.adapter = this.searchAdapter
        rvSearchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}
