package miinjung.study.test.model

import com.google.gson.annotations.SerializedName

data class Owner(
    val login: String? = null,
    @SerializedName("avatar_url") val avatarUrl:String? = null
)