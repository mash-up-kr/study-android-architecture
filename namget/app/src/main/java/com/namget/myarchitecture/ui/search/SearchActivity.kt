package com.namget.myarchitecture.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.namget.myarchitecture.R

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

class SearchActivity : AppCompatActivity() {

    private lateinit var menuSearch: MenuItem
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        init()
    }

    private fun init() {
        initView()
    }

    private fun initView() {

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuSearch = menu.findItem(R.id.menu_search)
        searchView = menuSearch.actionView as SearchView

        menuSearch.expandActionView()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }


}