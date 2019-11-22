package com.example.mashuparchitecture.data.source.vo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.network.vo.GithubUserResponse
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "history_table")
data class GithubRepoEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("owner")
    val owner: GithubRepositoriesResponse.Item.Owner,
    @SerializedName("description")
    val description: String?,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("language")
    val language: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(GithubRepositoriesResponse.Item.Owner::class.java.classLoader)!!,
        parcel.readString(),
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(description)
        parcel.writeLong(updatedAt.time)
        parcel.writeInt(stargazersCount)
        parcel.writeInt(watchersCount)
        parcel.writeString(language)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GithubRepoEntity> {
        override fun createFromParcel(parcel: Parcel): GithubRepoEntity {
            return GithubRepoEntity(parcel)
        }

        override fun newArray(size: Int): Array<GithubRepoEntity?> {
            return arrayOfNulls(size)
        }
    }

    fun convertItemIntoDetailRepoVo(
        githubUserResponse: GithubUserResponse
    ) = GithubDetailRepoVo(
        id,
        name,
        fullName,
        owner,
        description,
        SimpleDateFormat("yyyy-MM-dd E요일 HH:mm", Locale.KOREA).format(updatedAt),
        "★ $stargazersCount stars",
        watchersCount,
        language ?: "No language specified",
        githubUserResponse.name ?: "No name specified",
        "Followers : ${githubUserResponse.followers}",
        "Following : ${githubUserResponse.following}"
    )
}