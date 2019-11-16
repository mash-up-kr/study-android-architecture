package com.example.mashuparchitecture.base

interface BaseView <T>{

    fun setPresenter()

    fun showToastMessageFromView(msg: String)

}