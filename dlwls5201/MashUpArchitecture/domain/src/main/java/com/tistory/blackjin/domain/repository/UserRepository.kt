package com.tistory.blackjin.domain.repository

import com.tistory.blackjin.domain.entity.UserEntity
import io.reactivex.Single

interface UserRepository {

    fun getUser(username: String): Single<UserEntity>
}
