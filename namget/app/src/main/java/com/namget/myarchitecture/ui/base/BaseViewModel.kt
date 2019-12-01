package com.namget.myarchitecture.ui.base

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Namget on 2019.12.01.
 */
abstract class BaseViewModel : BaseObservable(){
    protected val disposable = CompositeDisposable()
    protected val isLoading = ObservableField<Boolean>(false)


}