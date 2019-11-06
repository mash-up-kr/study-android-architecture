package com.namget.myarchitecture.ext

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Namget on 2019.10.23.
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}