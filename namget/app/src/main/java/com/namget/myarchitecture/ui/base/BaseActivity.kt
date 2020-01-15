package com.namget.myarchitecture.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.namget.myarchitecture.BR
import com.namget.myarchitecture.ext.hideKeyboard
import com.namget.myarchitecture.ext.showKeyboard
import com.namget.myarchitecture.ext.showToast

/**
 * Created by Namget on 2019.10.23.
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes
    val layoutRes: Int
) :
    AppCompatActivity() {
    abstract val viewModel: VM
    protected open lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.setVariable(BR.viewmodel, viewModel)
    }

    protected val toast: (Int) -> Unit = {
        showToast(it)
    }

    protected val keyBoard: (Boolean) -> Unit = {
        if (it) showKeyboard() else hideKeyboard()
    }

    override fun onDestroy() {
        viewModel.unSubscribe()
        super.onDestroy()
    }

}