package com.tistory.blackjin.data

import com.tistory.blackjin.domain.error.ErrorEntity
import io.reactivex.*
import retrofit2.HttpException

internal class NetworkExceptionSingleTransformer<T> : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): SingleSource<T> =
        upstream.onErrorResumeNext {
            Single.error(
                if (it is HttpException) {
                    if (it.code() == 403) {
                        ErrorEntity.RateLimitException(it)
                    } else {
                        ErrorEntity.NetworkException(it)
                    }
                } else {
                    ErrorEntity.Unknown(it)
                }
            )
        }
}

internal class NetworkExceptionCompletableTransformer : CompletableTransformer {
    override fun apply(upstream: Completable): CompletableSource =
        upstream.onErrorResumeNext {
            Completable.error(it)
        }
}

internal fun <T> Single<T>.composeDomain() =
    compose(NetworkExceptionSingleTransformer())

internal fun Completable.composeDomain() =
    compose(NetworkExceptionCompletableTransformer())
