package com.example.myapplication.ui

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

interface NetworkException {
    fun showProgress(pb : ProgressBar) {
        pb.visibility = View.VISIBLE
    }

    fun hideProgress(pb : ProgressBar) {
        pb.visibility = View.GONE
    }

    fun showError(tv : TextView){
        tv.visibility = View.VISIBLE
    }

    fun hideError(tv: TextView){
        tv.visibility = View.GONE
    }
}