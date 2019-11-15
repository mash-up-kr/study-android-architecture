package miinjung.study.test.model

import com.google.gson.annotations.SerializedName

data class List (
    @SerializedName("total_count") val totalCount: Int = 0,
    val items : ArrayList<Item>
)