package com.tistory.blackjin.domain.interactor.usecases

import com.tistory.blackjin.domain.entity.RepoEntity
import com.tistory.blackjin.domain.entity.UserEntity
import com.tistory.blackjin.domain.interactor.SingleUseCase
import com.tistory.blackjin.domain.repository.RepoRepository
import com.tistory.blackjin.domain.repository.UserRepository
import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class GetRepoUsecase(
    private val userRepository: UserRepository,
    private val repoRepository: RepoRepository,
    schedulersProvider: SchedulersProvider
) : SingleUseCase<Pair<UserEntity, RepoEntity>, Pair<String, String>>(
    schedulersProvider
) {
    override fun buildUseCaseSingle(params: Pair<String, String>): Single<Pair<UserEntity, RepoEntity>> {
        val (ownerName, repoName) = params

        return Single.zip(
            userRepository.getUser(ownerName),
            repoRepository.getRepo(ownerName, repoName),
            BiFunction<UserEntity, RepoEntity, Pair<UserEntity, RepoEntity>> { t1, t2 ->
                Pair(t1, t2)
            }
        )
    }
}