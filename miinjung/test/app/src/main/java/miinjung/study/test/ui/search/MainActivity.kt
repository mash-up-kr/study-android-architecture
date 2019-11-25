package miinjung.study.test.ui.search


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import miinjung.study.test.R
import miinjung.study.test.model.Item

class MainActivity : AppCompatActivity(),SearchContract.View{

    private lateinit var presenter: SearchPresenter
    private lateinit var searchAdapter:SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = SearchPresenter(this)


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

                showRecyclerView()
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
                showRecyclerView()
                menuView.collapseActionView()
                queryTextSubmit(query)
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

    override fun onStop() {
        super.onStop()
        presenter.apiStop()
    }

    override fun showRecyclerView(){
        tvText.visibility = View.INVISIBLE
        rvSearchList.visibility = View.VISIBLE
    }

    override fun showTextView(){
        tvText.visibility = View.VISIBLE
        rvSearchList.visibility = View.INVISIBLE
    }

    override fun showProgress() {
        pbActivitySearch.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbActivitySearch.visibility = View.GONE
    }


    override fun initRecycleview(){
        searchAdapter = SearchAdapter(this.applicationContext)

        rvSearchList.adapter = this.searchAdapter
        rvSearchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun rvDataBinding(item : ArrayList<Item>) {
        searchAdapter!!.setItems(item)
        searchAdapter!!.setItems(item)
        searchAdapter!!.notifyDataSetChanged()
    }

    override fun queryTextSubmit(query: String) {
        supportActionBar?.setTitle("search")
        supportActionBar?.run{ subtitle = query }
        presenter.searchRepos(query)
    }

}
