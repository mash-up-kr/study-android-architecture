package com.tistory.blackjin.domain.error

sealed class ErrorEntity(throwable: Throwable?) : Throwable(throwable) {

    class NetworkException(throwable: Throwable? = null) : ErrorEntity(throwable)

    class RateLimitException(throwable: Throwable? = null) : ErrorEntity(throwable)

    class Unknown(throwable: Throwable? = null) : ErrorEntity(throwable)
}