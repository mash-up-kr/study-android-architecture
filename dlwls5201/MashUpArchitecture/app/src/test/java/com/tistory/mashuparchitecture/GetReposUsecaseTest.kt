package com.tistory.mashuparchitecture

import com.tistory.blackjin.domain.interactor.usecases.GetReposUsecase
import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetReposUsecaseTest {

    @Mock
    private lateinit var repoRepository: RepoRepository

    private lateinit var usecase: GetReposUsecase

    @Before
    fun setUp() {
        usecase = GetReposUsecase(repoRepository, TestSchedulerProvider())
    }

    @Test
    fun `success repo repository`() {

        `when`(repoRepository.searchRepos("")).thenReturn(Single.just(emptyList()))
        `when`(usecase.get("")).thenReturn(Single.just(emptyList()))

    }
}