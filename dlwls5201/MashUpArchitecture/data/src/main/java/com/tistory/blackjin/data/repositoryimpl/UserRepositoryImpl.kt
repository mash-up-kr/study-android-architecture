package com.tistory.blackjin.data.repositoryimpl

import com.tistory.blackjin.data.composeDomain
import com.tistory.blackjin.data.model.mapToDomain
import com.tistory.blackjin.data.source.remote.UserApi
import com.tistory.blackjin.domain.entity.UserEntity
import com.tistory.blackjin.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override fun getUser(username: String): Single<UserEntity> {
        return userApi.getUser(username)
            .map { it.mapToDomain() }
            .composeDomain()
    }
}