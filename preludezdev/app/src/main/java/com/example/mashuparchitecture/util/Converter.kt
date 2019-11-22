package com.example.mashuparchitecture.util

import androidx.room.TypeConverter
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import java.util.*

class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromStringToOwner(value: String?): GithubRepositoriesResponse.Item.Owner? {
        val arr = value?.split("\n")

        return if (!arr.isNullOrEmpty()) {
            GithubRepositoriesResponse.Item.Owner(arr[0].toInt(), arr[1], arr[2])
        } else null
    }

    @TypeConverter
    fun ownerToString(owner: GithubRepositoriesResponse.Item.Owner): String? {
        val sb = StringBuffer()
        sb.append(owner.id)
            .append("\n")
            .append(owner.login)
            .append("\n")
            .append(owner.avatarUrl)

        return sb.toString()
    }

}