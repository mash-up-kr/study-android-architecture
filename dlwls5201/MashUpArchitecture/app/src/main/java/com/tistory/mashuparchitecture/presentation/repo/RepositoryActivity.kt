package com.tistory.mashuparchitecture.presentation.repo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tistory.blackjin.domain.interactor.usecases.GetRepoUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.util.ResourcesProvider
import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.model.UserItem
import kotlinx.android.synthetic.main.activity_repository.*
import org.koin.android.ext.android.inject

class RepositoryActivity : AppCompatActivity(), RepositoryContract.View {

    override lateinit var presenter: RepositoryContract.Presenter

    private val getRepoUsecase: GetRepoUsecase by inject()

    private val resourceProvider: ResourcesProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        val login = intent.getStringExtra(KEY_OWNER_NAME) ?: throw IllegalArgumentException(
            "No login info exists in extras"
        )
        val repo = intent.getStringExtra(KEY_REPO_NAME) ?: throw IllegalArgumentException(
            "No repo info exists in extras"
        )

        presenter = RepositoryPresenter(
            this,
            getRepoUsecase,
            resourceProvider
        )

        presenter.loadRepository(login, repo)
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    override fun showRepositoryInfo(user: UserItem, repo: RepoItem) {

        Glide.with(this@RepositoryActivity)
            .load(user.profileUrl)
            .into(ivActivityRepositoryProfile)

        tvActivityRepositoryName.text = user.name

        tvActivityRepositoryFollower.text = user.followers

        tvActivityRepositoryFollowing.text = user.following

        tvActivityRepositoryTitle.text = repo.title

        tvActivityRepositoryStars.text = repo.stars

        tvActivityRepositoryDescription.text = repo.description

        tvActivityRepositoryLanguage.text = repo.language

        tvActivityRepositoryLastUpdate.text = repo.updatedAt
    }

    override fun showProgress() {
        llActivityRepositoryContent.visibility = View.GONE
        pbActivityRepository.visibility = View.VISIBLE
    }

    override fun hideProgress(isSucceed: Boolean) {
        llActivityRepositoryContent.visibility = if (isSucceed) View.VISIBLE else View.GONE
        pbActivityRepository.visibility = View.GONE
    }

    override fun showError(message: String?) {
        with(tvActivityRepositoryMessage) {
            text = message ?: "Unexpected error."
            visibility = View.VISIBLE
        }
    }

    companion object {

        const val KEY_OWNER_NAME = "owner_name"

        const val KEY_REPO_NAME = "repo_name"

        fun startRepositoryActivity(context: Context, ownerName: String, repoName: String) {
            context.startActivity(
                Intent(context, RepositoryActivity::class.java).apply {
                    putExtra(KEY_OWNER_NAME, ownerName)
                    putExtra(KEY_REPO_NAME, repoName)
                }
            )
        }
    }
}
