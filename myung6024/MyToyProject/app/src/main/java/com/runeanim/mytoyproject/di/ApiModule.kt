package com.runeanim.mytoyproject.di

import com.runeanim.mytoyproject.data.source.remote.api.SearchAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiModule = module {
    single<SearchAPI>(createdAtStart = false) { get<Retrofit>().create(SearchAPI::class.java) }
}
