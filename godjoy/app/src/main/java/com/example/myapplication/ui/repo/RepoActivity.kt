package com.example.myapplication.ui.repo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.model.SearchRepo
import com.example.myapplication.R
import com.example.myapplication.ui.NetworkException
import com.example.myapplication.ui.search.SearchRecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_repo.*

class RepoActivity : AppCompatActivity(), NetworkException, RepoContract.View {

    private val presenter by lazy {
        RepoPresenter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        presenter.start()

    }

    override fun init() {
        presenter.requestRepoData(
            SearchRecyclerviewAdapter.queryUserName,
            SearchRecyclerviewAdapter.queryRepoName
         )
    }

    override fun loadRepoData(repoData : SearchRepo?) {
        repoData?.let {
            Glide
                .with(this@RepoActivity)
                .load(it.owner.userImg).centerCrop()
                .into(ivRepoActivity)

            tvNameRepoActivity.text = getString(R.string.user_name, it.owner.userName)
            tvRepoNameRepoActivity.text = it.name
            tvStarNumRepoActivity.text = getString(R.string.star_num, it.starNum)

            tvIsNullOrEmpty(it, tvLanguageRepoActivity,"No language specified" )
            tvIsNullOrEmpty(it, tvDescriptionRepoActivity, "No description")

            tvUpdateDateRepoActivity.text = it.updateTime.subSequence(0,10)
            tvUpdateTimeRepoActivity.text = it.updateTime.subSequence(11,19)
        }
    }

    private fun tvIsNullOrEmpty(repoData: SearchRepo, tv : TextView, msg : String){
        tv.text =
            if(repoData.language.isNullOrEmpty())
               msg
            else repoData.language
    }

    override fun showError() {
        llRepoActivity.visibility = View.GONE
        tvNoResultActivityRepo.visibility = View.VISIBLE
    }

    override fun hideError() {
        tvNoResultActivityRepo.visibility = View.GONE
    }

    override fun showProgress() {
        pbActivityRepo.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbActivityRepo.visibility = View.GONE
    }
}
