package com.runeanim.mytoyproject

import androidx.annotation.VisibleForTesting
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Error
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.data.source.RepositoriesRepository
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.data.source.remote.response.RepositoriesResponse
import java.util.*
import kotlin.collections.ArrayList

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeRepository : RepositoriesRepository {

    var repositoryData: ArrayList<RepositoryEntity> = ArrayList()

    private var shouldReturnError = false

    lateinit var repository: Repository
    private val owner = Owner("", "", 1, "", "", "", 0, 0)

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getRepositories(): Result<List<RepositoryEntity>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(repositoryData.toList())
    }

    override suspend fun saveRepository(repositoryEntity: RepositoryEntity) {
        repositoryData.add(repositoryEntity)
    }

    override suspend fun removeAllRepositories() {
        repositoryData.clear()
    }

    /* 8개의 기본 데이터를 가지고 있다고 가정합니다. */
    override suspend fun searchRepositories(searchKeyWord: String): Result<RepositoriesResponse> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }

        val dummySearchList = ArrayList<Repository>()
        dummySearchList.add(Repository(1, "testName1", "testName1", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName2", "testName2", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName3", "testName3", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName4", "testName4", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName5", "testName5", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName6", "testName6", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName7", "testName7", owner, null, 0, null, Date()))
        dummySearchList.add(Repository(1, "testName8", "testName8", owner, null, 0, null, Date()))

        val filterList = ArrayList(dummySearchList.filter { it.fullName.contains(searchKeyWord) })
        return Success(RepositoriesResponse(3, true, filterList))
    }

    override suspend fun getRepositoryInfo(repoUrl: String): Result<Repository> {
        repository = Repository(1, "testName", repoUrl, owner, null, 0, null, Date())
        return Success(repository)
    }

    override suspend fun getUserInfo(userId: String): Result<Owner> {
        return Success(owner)
    }

    // 원래는 private이어야 하지만 테스트를 위해 package-private으로 가시성을 완화한다
    @VisibleForTesting
    // varang 가변 인자
    // 인자가 몇개 들어올지 모름
    // parameter 앞에 varang을 붙여주면 된다.
    // 그러면 해당 값은 Array<out T> 형태가 되고
    // 배열 처럼 값을 읽으면 된다.
    // 만약 보내는 쪽에서
    // val strings = arrayOf("vararg1", "vararg2", "vararg3")
    // 이런 배열 값을 보내고 싶다면
    // addRepos(*strings) 처럼 앞에 *을 붙여주면 가변인자로 인식한다.
    fun addRepos(vararg repos: RepositoryEntity) {
        for (repo in repos) {
            repositoryData.add(repo)
        }
    }
}
