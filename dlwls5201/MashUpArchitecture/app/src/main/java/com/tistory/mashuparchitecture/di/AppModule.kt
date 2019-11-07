package com.tistory.mashuparchitecture.di

import com.tistory.blackjin.domain.schedulers.SchedulersProvider
import com.tistory.mashuparchitecture.AppSchedulerProvider
import com.tistory.mashuparchitecture.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("debug")) { BuildConfig.DEBUG }
    single<SchedulersProvider> { AppSchedulerProvider() }
    single<ResourcesProvider> { ResourcesProviderImpl(androidApplication()) }
}