package com.namget.myarchitecture.ui.base

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Namget on 2019.10.23.
 */
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity() {

    abstract val presenter : P


    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

}