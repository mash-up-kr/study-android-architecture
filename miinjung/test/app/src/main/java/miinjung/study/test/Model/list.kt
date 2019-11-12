package miinjung.study.test.Model

import com.google.gson.annotations.SerializedName

data class list (
    @SerializedName("total_count") val totalCount: Int = 0,
    val items : ArrayList<item>
)