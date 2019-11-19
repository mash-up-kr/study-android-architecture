package com.tistory.blackjin.data.di

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tistory.blackjin.data.model.RepoHistory
import com.tistory.blackjin.data.source.local.RepoDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single {
        get<RepoDatabase>().repoData()
    }

    //Room 함수를 data 모듈에서 불러올 수가 없어 app 모듈 di 에서 초기화 해줍니다.
    single {
        Room.databaseBuilder(
            androidApplication(),
            RepoDatabase::class.java,
            "repo.db"
        ).build()
    }
}

@Database(entities = [RepoHistory::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoData(): RepoDao
}