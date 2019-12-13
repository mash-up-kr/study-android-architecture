package com.namget.myarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.repository.RepoRepositoryImpl
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.databinding.ActivityMainBinding
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.base.RepoRepositoryInf
import com.namget.myarchitecture.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    RepoRepositoryInf {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private val repoList: MutableList<RepoItemEntity> = arrayListOf()
    override val viewModel: MainViewModel by lazy {
        MainViewModel(repoRepository, toast)

    }
    override val repoRepository: RepoRepository by lazy {
        RepoRepositoryImpl
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initEvent()
        initRecyclerView()
        viewModel.selectRepoData()
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

}
