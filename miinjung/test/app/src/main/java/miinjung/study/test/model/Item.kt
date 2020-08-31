package miinjung.study.test.model

import com.google.gson.annotations.SerializedName

data class Item (
    val name: String,
    @SerializedName("full_name") val fullName:String,
    val owner: Owner,
    val description: String,
    val language: String,
    @SerializedName("updated_at") val updatedAt:String,
    @SerializedName("stargazers_count") val stargazersCount: String
)