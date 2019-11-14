package com.runeanim.mytoyproject.ui.detail

import com.runeanim.mytoyproject.base.BasePresenter
import com.runeanim.mytoyproject.base.BaseView
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository

interface DetailContract {

    interface View : BaseView {

        fun showError()

        fun setDataBindingItems(repoResult: Result<Repository>, userResult: Result<Owner>)
    }

    interface Presenter : BasePresenter {

        fun getUserAndRepositoryInfo()
    }
}