package com.example.myapplication.ui.Base

interface BaseContract {
    interface View{
        fun init()
        fun showError()
        fun hideError()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter{
        fun start()
    }
}