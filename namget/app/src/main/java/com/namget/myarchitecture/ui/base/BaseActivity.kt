package com.namget.myarchitecture.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.namget.myarchitecture.ui.main.MainViewModel

/**
 * Created by Namget on 2019.10.23.
 */
abstract class BaseActivity<M : BaseViewModel> : AppCompatActivity() ,  {

    abstract val viewModel : M

    override fun onDestroy() {
        viewModel.unSubscribe()
        super.onDestroy()
    }

}