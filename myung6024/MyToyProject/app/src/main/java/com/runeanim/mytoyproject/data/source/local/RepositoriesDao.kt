package com.runeanim.mytoyproject.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

@Dao
interface RepositoriesDao {

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM repos")
    suspend fun getRepositories(): List<RepositoryEntity>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM repos WHERE fullName = :fullName")
    suspend fun getRepositoryById(fullName: String): RepositoryEntity?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepository(task: RepositoryEntity)

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM repos WHERE fullName = :fullName")
    suspend fun deleteRepositoryById(fullName: String): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM repos")
    suspend fun deleteRepositories()
}