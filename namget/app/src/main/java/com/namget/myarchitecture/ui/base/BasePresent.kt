package com.namget.myarchitecture.ui.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Namget on 2019.11.12.
 */
abstract class BasePresent {
    protected val disposable = CompositeDisposable()
}