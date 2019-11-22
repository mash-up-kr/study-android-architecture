package com.example.mashuparchitecture.network.vo


import android.os.Parcel
import android.os.Parcelable
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.google.gson.annotations.SerializedName
import java.util.*

data class GithubRepositoriesResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>
) {
    data class Item(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("owner")
        val owner: Owner,
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
    ) {
        data class Owner(
            @SerializedName("id")
            val id: Int,
            @SerializedName("login")
            val login: String?,
            @SerializedName("avatar_url")
            val avatarUrl: String?
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString()
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(login)
                parcel.writeString(avatarUrl)
            }

            override fun describeContents(): Int = 0

            companion object CREATOR : Parcelable.Creator<Owner> {
                override fun createFromParcel(parcel: Parcel): Owner {
                    return Owner(parcel)
                }

                override fun newArray(size: Int): Array<Owner?> {
                    return arrayOfNulls(size)
                }
            }
        }

        fun convertItemIntoRepoEntity() = GithubRepoEntity(
            id,
            name,
            fullName,
            owner,
            description,
            updatedAt,
            stargazersCount,
            watchersCount,
            language
        )

    }
}