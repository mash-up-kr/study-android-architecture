package com.tistory.blackjin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tistory.blackjin.domain.entity.RepoHistoryEntity

@Entity(tableName = "repos")
data class RepoHistory(
    @PrimaryKey @ColumnInfo(name = "full_name")
    val repoName: String,
    @ColumnInfo(name = "owner_name")
    val ownerName: String,
    @ColumnInfo(name = "profile_url")
    val profileUrl: String,
    val language: String?
)

fun List<RepoHistory>.mapToDomain() = map { it.mapToDomain() }

fun RepoHistory.mapToDomain() =
    RepoHistoryEntity(
        repoName,
        ownerName,
        profileUrl,
        language
    )