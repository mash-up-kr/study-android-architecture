package com.namget.myarchitecture.ui.repo

import android.os.Bundle
import android.view.MenuItem
import coil.api.load
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.response.RepoInfoResponse
import com.namget.myarchitecture.data.response.UserInfoResponse
import com.namget.myarchitecture.ext.dateToNumberFormat
import com.namget.myarchitecture.ext.setVisible
import com.namget.myarchitecture.ext.showToast
import com.namget.myarchitecture.ui.base.BaseActivity
import com.namget.myarchitecture.util.URL_REPO_DATA
import com.namget.myarchitecture.util.URL_USER_DATA
import kotlinx.android.synthetic.main.activity_repo.*

/**
 * Created by Namget on 2019.10.22.
 */
class RepoActivity : BaseActivity<RepoPresenter>(), RepoContract.View {
    private lateinit var repoUrl: String
    private lateinit var userUrl: String
    override val presenter: RepoPresenter by lazy {
        RepoPresenter(repoRepository, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        init()
    }

    private fun init() {
        getIntentExtra()
        requestUserData()
    }

    override fun requestUserData() {
        if (::userUrl.isInitialized && ::repoUrl.isInitialized) {
            presenter.requestUserData(userUrl,repoUrl)
        }
    }

    override fun showUserInfoData(userInfoResponse: UserInfoResponse) {
        with(userInfoResponse) {
            repoProfileImage.load(avatarUrl)
            repoFollowerTitle.text =
                String.format(getString(R.string.follow_format), followers, following)
            repoUserName.text = name
        }
    }

    override fun showRepoInfoData(repoInfoResponse: RepoInfoResponse) {
        with(repoInfoResponse) {
            repoProfileTitle.text = fullName
            repoProfileStars.text =
                String.format(getString(R.string.stars_format), starCount)
            repoDescriptionTitle.text = description
            repoLanguageTitle.text =
                if (language.isNullOrBlank()) "No language specified" else language
            repoLastUpdateTitle.text = updateTime.dateToNumberFormat()
        }
    }

    private fun getIntentExtra() {
        if (intent != null && intent.hasExtra(URL_REPO_DATA) && intent.hasExtra(URL_USER_DATA)) {
            repoUrl = intent.getStringExtra(URL_REPO_DATA)!!
            userUrl = intent.getStringExtra(URL_USER_DATA)!!
        }
    }

    override fun makeToast(resId: Int) = showToast(resId)

    override fun showDialog() {
        progressBar.setVisible(true)
        repoLayout.setVisible(false)
    }

    override fun hideDialog() {
        progressBar.setVisible(false)
        repoLayout.setVisible(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}