package com.namget.myarchitecture.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.namget.myarchitecture.data.repository.RepoRepository
import com.namget.myarchitecture.data.repository.RepoRepositoryImpl

/**
 * Created by Namget on 2019.10.23.
 */
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity() {

    abstract val presenter : P
    protected val repoRepository: RepoRepository by lazy { RepoRepositoryImpl }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

}