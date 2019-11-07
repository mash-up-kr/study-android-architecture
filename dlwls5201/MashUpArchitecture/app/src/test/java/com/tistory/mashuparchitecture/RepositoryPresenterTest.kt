package com.tistory.mashuparchitecture

import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.entity.UserEntity
import com.tistory.blackjin.domain.error.ErrorEntity
import com.tistory.blackjin.domain.interactor.usecases.GetRepoUsecase
import com.tistory.mashuparchitecture.di.ResourcesProvider
import com.tistory.mashuparchitecture.model.mapToPresentation
import com.tistory.mashuparchitecture.presentation.repo.RepositoryContract
import com.tistory.mashuparchitecture.presentation.repo.RepositoryPresenter
import io.reactivex.Single
import org.junit.Assert.assertEquals
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
class RepositoryPresenterTest {

    @Mock
    private lateinit var view: RepositoryContract.View

    @Mock
    private lateinit var resourcesProvider: ResourcesProvider

    @Mock
    private lateinit var usecase: GetRepoUsecase

    private lateinit var presenter: RepositoryContract.Presenter

    private lateinit var inOrder: InOrder

    @Before
    fun setUp() {
        presenter = RepositoryPresenter(view, usecase, resourcesProvider)
        inOrder = Mockito.inOrder(view)
    }

    private val ownerName = "kunny"
    private val repoName = "kotlin"

    private val userEntity = UserEntity(ownerName, "", 0, 0)
    private val repoEntity = RepoEntity(repoName, RepoEntity.OwnerEntity(ownerName, ""), "", "", Date(), 0)

    @Test
    fun `success get repository`() {

        //given
        val result = Single.just(Pair(userEntity, repoEntity))
        `when`(usecase.get(Pair(ownerName, repoName))).thenReturn(result)

        //when
        presenter.loadRepository(ownerName, repoName)

        //then
        inOrder.verify(view).showProgress()
        inOrder.verify(view).hideProgress(true)

        val resultUser = userEntity.mapToPresentation(resourcesProvider)
        val resultRepo = repoEntity.mapToPresentation(resourcesProvider)

        inOrder.verify(view).showRepositoryInfo(resultUser, resultRepo)

        //check data
        assertEquals(resultRepo.title, "$ownerName/$repoName")
    }

    private val throwable = ErrorEntity.RateLimitException()

    @Test
    fun `fail get repository`() {

        //given
        `when`(usecase.get(Pair(ownerName, repoName)))
            .thenReturn(Single.error<Pair<UserEntity, RepoEntity>>(throwable))

        `when`(resourcesProvider.getString(R.string.rate_limit_error))
            .thenReturn("rate_limit_error")

        //when
        presenter.loadRepository(ownerName, repoName)

        //then
        inOrder.verify(view).showProgress()
        inOrder.verify(view).hideProgress(false)
        inOrder.verify(view).showError("rate_limit_error")

    }
}