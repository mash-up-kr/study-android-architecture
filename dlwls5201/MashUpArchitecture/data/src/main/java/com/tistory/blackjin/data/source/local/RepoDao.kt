package com.tistory.blackjin.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tistory.blackjin.data.model.RepoHistory
import io.reactivex.Flowable

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(repo: RepoHistory)

    @Query("SELECT * FROM repos")
    fun getRepos(): Flowable<List<RepoHistory>>

    @Query("DELETE FROM repos")
    fun clearAll()
}