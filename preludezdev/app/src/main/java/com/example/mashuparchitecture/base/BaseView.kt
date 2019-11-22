package com.example.mashuparchitecture.base

import androidx.annotation.StringRes

interface BaseView <T>{

    fun setPresenter()

    fun showToastMessageFromView(@StringRes resId: Int)

}