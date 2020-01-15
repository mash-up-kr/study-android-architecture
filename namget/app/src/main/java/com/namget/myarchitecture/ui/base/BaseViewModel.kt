package com.namget.myarchitecture.ui.base

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Namget on 2019.12.01.
 */
abstract class BaseViewModel : BaseObservable(){
    protected val disposable = CompositeDisposable()
    val isLoading = ObservableBoolean(false)

    protected fun showLoading(){
        isLoading.set(true)
    }

    protected fun hideLoading(){
        isLoading.set(false)
    }


    fun unSubscribe(){
        disposable.dispose()
    }
}