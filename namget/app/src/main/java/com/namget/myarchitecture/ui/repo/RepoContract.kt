package com.namget.myarchitecture.ui.repo

import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import com.namget.myarchitecture.ui.base.BasePresenter
import com.namget.myarchitecture.ui.base.BaseView

/**
 * Created by Namget on 2019.11.12.
 */
interface RepoContract{
    interface View : BaseView<Presenter> {
        fun setUserInfoData(userInfoResponse: UserInfoResponse)
        fun setRepoInfoData(repoInfoResponse: RepoInfoResponse)
        fun requestUserData()
    }

    interface Presenter : BasePresenter {
        fun requestUserData(userUrl : String, repoUrl : String)
    }
}