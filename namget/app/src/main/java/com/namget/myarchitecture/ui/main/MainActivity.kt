package com.namget.myarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.ext.e
import com.namget.myarchitecture.ext.makeToast
import com.namget.myarchitecture.ext.plusAssign
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {



    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private val repoList: MutableList<RepoItemEntity> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initEvent()
        initRecyclerView()
        selectRepoData()
    }

    private fun initRecyclerView() {
        mainAdapter = MainAdapter(repoList)
        recyclerView = mainRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = mainAdapter
        }
    }

    private fun initEvent() {
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun selectRepoData() {
        disposable += repoRepository.selectRepoData()
            .subscribe({
                e(TAG, it.toString())
                mainAdapter.replaceItems(it)
            }, {
                makeToast(getString(R.string.error))
                e(TAG, "selectRepoData", it)
            }, {

            })
    }

    companion object {
        private val TAG = "MainActivity"
    }

}
