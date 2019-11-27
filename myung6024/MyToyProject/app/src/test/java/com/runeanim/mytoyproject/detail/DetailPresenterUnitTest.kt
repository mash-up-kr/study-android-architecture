package com.runeanim.mytoyproject.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.runeanim.mytoyproject.LiveDataTestUtil
import com.google.common.truth.Truth
import com.runeanim.mytoyproject.FakeRepository
import com.runeanim.mytoyproject.MainCoroutineRule
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import com.runeanim.mytoyproject.ui.detail.DetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailPresenterUnitTest {

    private lateinit var viewModel: DetailViewModel

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
        val task1 = RepositoryEntity(1, "Description1", "one", null, "", 0)
        val task2 = RepositoryEntity(2, "Description2", "two", null, "", 1)
        val task3 = RepositoryEntity(3, "Description3", "three", null, "", 2)
        repoRepository.addRepos(task1, task2, task3)
        viewModel = DetailViewModel(
            "testUrl",
            "testId",
            GetRepositoryInfoUseCase(repoRepository),
            GetUserInfoUseCase(repoRepository)
        )
    }

    @Test
    fun `진입했을 때 프로그래스바 작동`() {
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
    }

    @Test
    fun `레포지토리 상세정보 불러오기`() {
        // Pause dispatcher so we can verify initial values
        // dispatcher를 잠시 멈추는 이유는 dataLoading 값을 확인하기 위해서이다.
        // getUserAndRepositoryInfo()가 실행되면 동시에 dataLoading 값이 true로 변하게 되는데
        // dispatcher를 멈추지 않고 true를 체크한다면 이미 getUserAndRepositoryInfo() 코루틴은 종료가 된 시점이기 때문에
        // false 상태만 보게 된다.
        mainCoroutineRule.pauseDispatcher()
        viewModel.getUserAndRepositoryInfo()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.isLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.repoInfo)).isEqualTo(repoRepository.repository)
    }
}