package com.runeanim.mytoyproject.main

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
class MainviewModelUnitTest {

    private lateinit var viewModel: RepoViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repoRepository: FakeRepository

    // view는 만들어서 넣어주기 힘드니 Mock으로 사용한다.

    // Set the main coroutines dispatcher for unit testing.
    // Coroutine 테스트를 위한 rule이다.
    // RxJava를 사용한 코드를 테스트하기 위해서 RxRule을 만들어 주는 것처럼
    // 코루틴도 main 스레드를 사용하기 위한 rule이 필요하다.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //Android Architecture Component의 liveData를 사용할 때 postValue를 까보면 main thread로 값을 전달하게 되는데 이때
    //Method getMainLooper in android.os.Looper not mocked. 이라는 에러를 낸다.
    //InstantTaskExecutorRule은 aac에서 생기는 문제를 해결해주는 rule이다.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repoRepository = FakeRepository()
        val task1 = RepositoryEntity(1, "Description1", "one", null, "", 0)
        val task2 = RepositoryEntity(2, "Description2", "two", null, "", 1)
        val task3 = RepositoryEntity(3, "Description3", "three", null, "", 2)
        repoRepository.addRepos(task1, task2, task3)
        viewModel = RepoViewModel(
            SearchRepositoriesUseCase(repoRepository),
            SaveRepositoryUseCase(repoRepository),
            GetRepositoriesUseCase(repoRepository),
            RemoveAllRepositoriesUseCase(repoRepository)
        )
    }

    @Test
    fun `전체 삭제하기`() {
        viewModel.removeAllRepositoryHistory()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)
    }

    @Test
    fun `저장된 목록 불러오기`() {
        viewModel.getClickedRepositoryHistory()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(3)
    }

    @Test
    fun `전체 삭제하기 + 저장된 목록 불러오기`() {
        viewModel.removeAllRepositoryHistory()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)

        viewModel.getClickedRepositoryHistory()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)
    }

    @Test
    fun `저장된 목록 불러오기 + 전체 삭제하기`() {
        viewModel.getClickedRepositoryHistory()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(3)

        viewModel.removeAllRepositoryHistory()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.items)).hasSize(0)
    }
}