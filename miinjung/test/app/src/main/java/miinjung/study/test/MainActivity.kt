package miinjung.study.test

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import miinjung.study.test.Model.item
import miinjung.study.test.Model.list
import miinjung.study.test.network.Controller
import miinjung.study.test.network.ServerInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class MainActivity : AppCompatActivity(){

    var api: ServerInterface? = Controller.instance?.buildServerInterface()
    internal var apiCall:Call<list>? = null
    internal var searchAdapter : SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchAdapter = SearchAdapter(this)

        rv_searchList.adapter = this.searchAdapter
        rv_searchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.bar_result,menu)

        val menuView = menu.findItem(R.id.search)
        val searchView = menuView.actionView as SearchView

        menuView.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        

        menuView?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                rv_searchList.visibility = View.VISIBLE
                tv_text.visibility = View.INVISIBLE
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
        apiCall = api?.search(query)
        apiCall?.enqueue(object :Callback<list>{
            override fun onResponse(call: Call<list>, response: Response<list>) {
                var data = response.body()
                Log.v("통신",data.toString())

                if(response.isSuccessful && data != null) {
                    if(data.total_count > 0){
                        var position = data.total_count
                        data.items.let{
                            searchAdapter!!.setItems(it)
                            searchAdapter!!.notifyItemInserted(position)
                        }
                    }else{
                        rv_searchList.visibility = View.INVISIBLE
                        tv_text.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<list>, t: Throwable) {
                Log.i("errer","error")
            }
        })
    }

}
