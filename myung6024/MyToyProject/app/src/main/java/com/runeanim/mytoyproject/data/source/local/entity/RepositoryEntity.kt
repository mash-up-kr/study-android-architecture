package com.runeanim.mytoyproject.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepositoryEntity(
    @ColumnInfo(name = "fullName") val fullName: String,
    val ownerName: String,
    val language: String?,
    val avatarUrl: String,
    @PrimaryKey(autoGenerate = true) val order: Int = 0
)
