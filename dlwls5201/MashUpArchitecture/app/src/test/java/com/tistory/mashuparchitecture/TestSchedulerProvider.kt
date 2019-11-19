package com.tistory.mashuparchitecture

import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulersProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()
}