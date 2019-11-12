package com.namget.myarchitecture.ui.main

import com.namget.myarchitecture.ui.base.BasePresenter
import com.namget.myarchitecture.ui.base.BaseView

/**
 * Created by Namget on 2019.11.12.
 */
interface MainContract {

    interface View : BaseView<Presenter> {


    }

    interface Presenter : BasePresenter {

    }

}