package com.namget.myarchitecture.ui.repo

import android.os.Bundle
import coil.api.load
import com.namget.myarchitecture.R
import com.namget.myarchitecture.ext.*
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.ui.base.BaseView
import com.namget.myarchitecture.util.URL_REPO_DATA
import com.namget.myarchitecture.util.URL_USER_DATA
import kotlinx.android.synthetic.main.activity_repo.*

/**
 * Created by Namget on 2019.10.22.
 */
class RepoActivity : BaseActivity(), BaseView {
    companion object {
        private const val TAG = "RepoActivity"
    }

    private lateinit var repoUrl: String
    private lateinit var userUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        init()
    }

    private fun init() {
        getIntentExtra()
        requestUserData()
    }

    private fun getIntentExtra() {
        if (intent != null && intent.hasExtra(URL_REPO_DATA) && intent.hasExtra(URL_USER_DATA)) {
            repoUrl = intent.getStringExtra(URL_REPO_DATA)!!
            userUrl = intent.getStringExtra(URL_USER_DATA)!!
        }
    }

    override fun showDialog() {
        progressBar.setVisible(true)
        repoLayout.setVisible(false)
    }

    override fun hideDialog() {
        progressBar.setVisible(false)
        repoLayout.setVisible(true)
    }


    private fun requestUserData() {
        e(TAG, "repoUrl : $repoUrl")
        e(TAG, "userUrl : $userUrl")

        if (::userUrl.isInitialized && ::repoUrl.isInitialized) {
            showDialog()
            disposable += repoRepository.getProfileInfo(userUrl, repoUrl)
                .subscribe({
                    with(it.first) {
                        //follower / following
                        repoProfileImage.load(avatarUrl)
                        repoFollowerTitle.text =
                            String.format(getString(R.string.follow_format), followers, following)
                        repoUserName.text = name
                    }
                    with(it.second) {
                        repoProfileTitle.text = fullName
                        repoUserName.text = name
                        repoProfileStars.text =
                            String.format(getString(R.string.stars_format), starCount)
                        repoDescriptionTitle.text = description
                        repoLanguageTitle.text = language
                        repoLastUpdateTitle.text = updateTime.dateToNumberFormat()
                    }
                    hideDialog()
                }, {
                    makeToast(getString(R.string.error))
                    e(TAG, "requestUserData", it)
                    hideDialog()
                })


        }
    }


}