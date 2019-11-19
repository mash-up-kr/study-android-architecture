package com.tistory.mashuparchitecture.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tistory.blackjin.domain.interactor.usecases.ClearRepoHistoryUsecase
import com.tistory.blackjin.domain.interactor.usecases.GetRepoHistoryUsecase
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.di.ResourcesProvider
import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.presentation.repo.RepositoryActivity
import com.tistory.mashuparchitecture.presentation.search.SearchActivity
import com.tistory.mashuparchitecture.presentation.search.SearchAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    private val getUsecase: GetRepoHistoryUsecase by inject()

    private val clearUsecase: ClearRepoHistoryUsecase by inject()

    private val resourcesProvider: ResourcesProvider by inject()

    private val searchAdapter by lazy { SearchAdapter(itemListener) }

    private val itemListener: (item: RepoItem) -> Unit =
        { item ->
            RepositoryActivity.startRepositoryActivity(
                this,
                item.owner.ownerName,
                item.repoName
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(
            this,
            getUsecase,
            clearUsecase,
            resourcesProvider
        )

        initRecyclerView()
        initButton()

        presenter.getRepoHistory()
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    private fun initRecyclerView() {
        rvActivityMainList.adapter = searchAdapter
    }

    private fun initButton() {
        btnActivityMainSearch.setOnClickListener {
            startActivity<SearchActivity>()
        }

        btnActivityMainClear.setOnClickListener {
            alert(message = "데이터를 모두 지웁니다.") {
                positiveButton("확인") {
                    presenter.clearAll()
                }

                negativeButton("취소") {

                }
            }.show()
        }
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun showRepoHistory(repos: List<RepoItem>) {
        searchAdapter.setItems(repos)
    }

    override fun showClearRepoHistory() {
        searchAdapter.setItems(emptyList())
    }

    override fun showEmptyMessage() {
        tvActivityMainMessage.visibility = View.VISIBLE
    }

    override fun hideEmptyMessage() {
        tvActivityMainMessage.visibility = View.GONE
    }

}
