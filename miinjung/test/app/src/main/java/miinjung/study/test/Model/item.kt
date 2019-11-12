package miinjung.study.test.Model

import com.google.gson.annotations.SerializedName

data class item (
    val name: String? = null,
    @SerializedName("full_name") val fullName:String? = null,
    val owner: owner? = null,
    val description: String? = null,
    val language: String? = null,
    @SerializedName("updated_at") val updatedAt:String? = null,
    @SerializedName("stargazers_count") val stargazersCount: String? = null
)