package com.namget.myarchitecture.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Namget on 2019.10.25.
 */
@Entity(tableName = "RepoItem")
data class RepoItemEntity(
    @PrimaryKey
    val fullName: String,
    val language: String,
    val avatarUrl: String
)