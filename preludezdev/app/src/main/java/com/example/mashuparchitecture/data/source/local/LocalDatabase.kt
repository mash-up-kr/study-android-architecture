package com.example.mashuparchitecture.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mashuparchitecture.data.source.local.dao.GithubRepoDao
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.example.mashuparchitecture.util.Converter

@Database(entities = [GithubRepoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getGithubRepoDao(): GithubRepoDao

}