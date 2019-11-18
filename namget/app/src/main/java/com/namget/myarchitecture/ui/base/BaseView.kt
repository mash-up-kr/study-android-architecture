package com.namget.myarchitecture.ui.base

import androidx.annotation.StringRes

interface BaseView<T> {
    fun showDialog()
    fun hideDialog()
    fun makeToast(@StringRes resId: Int)
}