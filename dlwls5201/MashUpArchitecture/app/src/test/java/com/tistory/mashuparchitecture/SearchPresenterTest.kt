package com.tistory.mashuparchitecture

import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.entity.UserEntity
import com.tistory.blackjin.domain.error.ErrorEntity
import com.tistory.blackjin.domain.interactor.usecases.GetReposUsecase
import com.tistory.mashuparchitecture.di.ResourcesProvider
import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.model.mapToPresentation
import com.tistory.mashuparchitecture.presentation.search.SearchContract
import com.tistory.mashuparchitecture.presentation.search.SearchPresenter
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTest {

    @Mock
    private lateinit var view: SearchContract.View

    @Mock
    private lateinit var resourcesProvider: ResourcesProvider

    @Mock
    private lateinit var usecase: GetReposUsecase

    private lateinit var presenter: SearchContract.Presenter

    private lateinit var inOrder: InOrder

    @Before
    fun setUp() {
        presenter = SearchPresenter(view, usecase, resourcesProvider)
        inOrder = Mockito.inOrder(view)
    }

    @Test
    fun `success get repository`() {

        //given
        val items = listOf(
            RepoEntity("", RepoEntity.OwnerEntity("", ""), "", "", Date(), 10)
        )

        val query = "kunny"
        val result = Single.just(items)
        `when`(usecase.get(query)).thenReturn(result)

        //when
        presenter.searchRepository(query)

        //then
        inOrder.verify(view).showProgress()
        inOrder.verify(view).hideProgress()

        val repos = items.mapToPresentation(resourcesProvider)
        inOrder.verify(view).showRepos(repos)

        inOrder.verify(view).showTopTitle(query)
        inOrder.verify(view).showCollapseSearchView()
        inOrder.verify(view).hideSoftKeyboard()

    }

    @Test
    fun `success get empty repository`() {

        //given
        val items = listOf<RepoEntity>()

        val query = "empty query"
        val result = Single.just(items)
        `when`(usecase.get(query)).thenReturn(result)

        `when`(resourcesProvider.getString(R.string.no_search_result))
            .thenReturn("no_search_result")

        //when
        presenter.searchRepository(query)

        //then
        inOrder.verify(view).showProgress()
        inOrder.verify(view).hideProgress()

        val repos = listOf<RepoItem>()
        inOrder.verify(view).showRepos(repos)

        inOrder.verify(view).showTopTitle(query)
        inOrder.verify(view).showCollapseSearchView()
        inOrder.verify(view).hideSoftKeyboard()

        inOrder.verify(view).showError("no_search_result")
    }
}