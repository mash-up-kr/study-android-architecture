package com.namget.myarchitecture.ui.repo

import android.os.Bundle
import android.view.MenuItem
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.repository.RepoRepositoryImpl
import com.namget.myarchitecture.databinding.ActivityRepoBinding
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.util.URL_REPO_DATA
import com.namget.myarchitecture.util.URL_USER_DATA

/**
 * Created by Namget on 2019.10.22.
 */
class RepoActivity : BaseActivity<ActivityRepoBinding, RepoViewModel>(R.layout.activity_repo) {
    private lateinit var repoUrl: String
    private lateinit var userUrl: String


    override val viewModel: RepoViewModel by lazy {
        RepoViewModel(repoRepository, toast)
    }
    private val repoRepository: RepoRepository by lazy {
        RepoRepositoryImpl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        getIntentExtra()
        requestUserData()
    }

    private fun requestUserData() {
        if (::userUrl.isInitialized && ::repoUrl.isInitialized) {
            viewModel.requestUserData(userUrl, repoUrl)
        }
    }

    private fun getIntentExtra() {
        if (intent != null && intent.hasExtra(URL_REPO_DATA) && intent.hasExtra(URL_USER_DATA)) {
            repoUrl = intent.getStringExtra(URL_REPO_DATA)!!
            userUrl = intent.getStringExtra(URL_USER_DATA)!!
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}