package com.runeanim.mytoyproject.di

import com.runeanim.mytoyproject.data.source.remote.api.GitHubAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiModule = module {
    single<GitHubAPI>(createdAtStart = false) { get<Retrofit>().create(GitHubAPI::class.java) }
}
