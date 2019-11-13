package com.namget.myarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.ext.setVisible
import com.namget.myarchitecture.ext.showToast
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private val repoList: MutableList<RepoItemEntity> = arrayListOf()
    private lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initEvent()
        initRecyclerView()
        setPresenter()
        mainPresenter.selectRepoData()
    }

    override fun showDialog() {
        mainRecyclerView.setVisible(false)
        progressBar.setVisible(false)
    }
    override fun hideDialog() {
        mainRecyclerView.setVisible(true)
        progressBar.setVisible(false)
    }
    override fun makeToast(resId: Int) = showToast(resId)
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

    override fun setPresenter() {
        mainPresenter = MainPresenter(repoRepository, this)
    }

    override fun replaceRepoItemList(replaceList: List<RepoItemEntity>) {
        mainAdapter.replaceItems(replaceList)
    }

    private fun initEvent() {
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
