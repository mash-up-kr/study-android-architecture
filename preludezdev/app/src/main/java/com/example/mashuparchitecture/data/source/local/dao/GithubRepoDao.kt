package com.example.mashuparchitecture.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import io.reactivex.Completable

@Dao
interface GithubRepoDao {
    @Query("SELECT * from history_table")
    fun loadMyGithubRepoList(): LiveData<List<GithubRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRepo(repo: GithubRepoEntity): Completable
}