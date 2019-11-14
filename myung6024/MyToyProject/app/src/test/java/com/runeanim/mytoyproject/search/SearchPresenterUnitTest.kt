package com.runeanim.mytoyproject.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.runeanim.mytoyproject.LiveDataTestUtil
import com.google.common.truth.Truth
import com.runeanim.mytoyproject.FakeRepository
import com.runeanim.mytoyproject.MainCoroutineRule
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import com.runeanim.mytoyproject.ui.search.SearchContract
import com.runeanim.mytoyproject.ui.search.SearchPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SearchPresenterUnitTest {

    private lateinit var presenter: SearchPresenter

    private lateinit var repoRepository: FakeRepository

    @Mock
    private lateinit var view: SearchContract.View

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repoRepository = FakeRepository()
        presenter = SearchPresenter(
            SearchRepositoriesUseCase(repoRepository),
            SaveRepositoryUseCase(repoRepository),
            view
        )
    }

    @Test
    fun `키워드로 검색하기`() {
        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(8)
    }

    @Test
    fun `키워드로 검색 - 검색 결과 없음`() {
        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("myung")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isEqualTo(R.string.no_search_result)
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(0)
    }

    @Test
    fun `키워드로 검색 - 에러`() {
        repoRepository.setReturnError(true)
        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("Description")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isEqualTo(R.string.no_search_result)
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(0)
    }

    @Test
    fun `키워드로 검색하기 + 검색 결과 없음`() {
        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(8)

        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("test10")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(0)
    }


    @Test
    fun `검색 결과 없음 + 키워드로 검색하기`() {
        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("test10")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(0)

        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(8)
    }

    @Test
    fun `검색 결과 1개 + 검색 결과 8개`() {
        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("testName1")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(1)

        mainCoroutineRule.pauseDispatcher()
        presenter.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.messageResId)).isNull()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.items)).hasSize(8)
    }

    @Test
    fun `아이템 저장하기`() {
        mainCoroutineRule.pauseDispatcher()
        presenter.onClickRepositoryItem(RepositoryEntity(4, "Description4", "four", null, "", 3))
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
    }
}