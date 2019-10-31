package com.runeanim.mytoyproject.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [RepositoryEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repositoriesDao(): RepositoriesDao
}