package com.runeanim.mytoyproject.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.runeanim.mytoyproject.LiveDataTestUtil
import com.google.common.truth.Truth
import com.runeanim.mytoyproject.FakeRepository
import com.runeanim.mytoyproject.MainCoroutineRule
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.domain.GetRepositoriesUseCase
import com.runeanim.mytoyproject.domain.RemoveAllRepositoriesUseCase
import com.runeanim.mytoyproject.domain.SaveRepositoryUseCase
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import com.runeanim.mytoyproject.ui.repo.RepoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchviewModelUnitTest {

    private lateinit var viewModel: RepoViewModel

    private lateinit var repoRepository: FakeRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repoRepository = FakeRepository()
        viewModel = RepoViewModel(
            SearchRepositoriesUseCase(repoRepository),
            SaveRepositoryUseCase(repoRepository),
            GetRepositoriesUseCase(repoRepository),
            RemoveAllRepositoriesUseCase(repoRepository)
        )
    }

    @Test
    fun `키워드로 검색하기`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(8)
    }

    @Test
    fun `키워드로 검색 - 검색 결과 없음`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("myung")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)
    }

    
    @Test
    fun `키워드로 검색 - 에러`() {
        repoRepository.setReturnError(true)
        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("Description")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)
    }

    @Test
    fun `키워드로 검색하기 + 검색 결과 없음`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(8)

        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("test10")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)
    }


    @Test
    fun `검색 결과 없음 + 키워드로 검색하기`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("test10")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)

        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(8)
    }

    @Test
    fun `검색 결과 1개 + 검색 결과 8개`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("testName1")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(1)

        mainCoroutineRule.pauseDispatcher()
        viewModel.searchRepositoryByKeyWord("test")
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorTextVisible)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(8)
    }

    @Test
    fun `아이템 저장하기`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.saveClickedRepositoryHistory(RepositoryEntity(4, "Description4", "four", null, "", 3))
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
    }
}