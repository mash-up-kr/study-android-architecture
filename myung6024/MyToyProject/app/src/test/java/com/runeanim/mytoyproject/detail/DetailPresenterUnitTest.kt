package com.runeanim.mytoyproject.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.runeanim.mytoyproject.LiveDataTestUtil
import com.google.common.truth.Truth
import com.runeanim.mytoyproject.FakeRepository
import com.runeanim.mytoyproject.MainCoroutineRule
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.domain.GetRepositoryInfoUseCase
import com.runeanim.mytoyproject.domain.GetUserInfoUseCase
import com.runeanim.mytoyproject.ui.detail.DetailContract
import com.runeanim.mytoyproject.ui.detail.DetailPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailPresenterUnitTest {

    private lateinit var presenter: DetailPresenter

    private lateinit var repoRepository: FakeRepository

    @Mock
    private lateinit var view: DetailContract.View

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
        presenter = DetailPresenter(
            "testUrl",
            "testId",
            GetRepositoryInfoUseCase(repoRepository),
            GetUserInfoUseCase(repoRepository),
            view
        )
    }

    @Test
    fun `진입했을 때 프로그래스바 작동`() {
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
    }

    @Test
    fun `레포지토리 상세정보 불러오기`() {
        // Pause dispatcher so we can verify initial values
        // dispatcher를 잠시 멈추는 이유는 dataLoading 값을 확인하기 위해서이다.
        // getUserAndRepositoryInfo()가 실행되면 동시에 dataLoading 값이 true로 변하게 되는데
        // dispatcher를 멈추지 않고 true를 체크한다면 이미 getUserAndRepositoryInfo() 코루틴은 종료가 된 시점이기 때문에
        // false 상태만 보게 된다.
        mainCoroutineRule.pauseDispatcher()
        presenter.getUserAndRepositoryInfo()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isTrue()
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.dataLoading)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.repoInfo)).isEqualTo(repoRepository.repository)
    }
}