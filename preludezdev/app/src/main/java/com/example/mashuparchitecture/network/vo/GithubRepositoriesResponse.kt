package com.example.mashuparchitecture.network.vo


import android.os.Parcel
import android.os.Parcelable
import com.example.mashuparchitecture.data.source.vo.DetailRepoVo
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
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
    ) : Parcelable {
        data class Owner(
            @SerializedName("login")
            val login: String?,
            @SerializedName("id")
            val id: Int,
            @SerializedName("avatar_url")
            val avatarUrl: String?
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readInt(),
                parcel.readString()
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(login)
                parcel.writeInt(id)
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

        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Owner::class.java.classLoader)!!,
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

        companion object CREATOR : Parcelable.Creator<Item> {
            override fun createFromParcel(parcel: Parcel): Item {
                return Item(parcel)
            }

            override fun newArray(size: Int): Array<Item?> {
                return arrayOfNulls(size)
            }
        }

        fun convertItemIntoDetailRepoVo(
            githubUserResponse: GithubUserResponse
        ) = DetailRepoVo(
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
}