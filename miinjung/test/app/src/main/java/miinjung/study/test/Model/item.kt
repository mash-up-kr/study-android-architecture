package miinjung.study.test.Model

data class item (
    val name: String,
    val full_name: String,
    val owner: owner,
    val description: String?,
    val language: String?,
    val updated_at: String,
    val stargazers_count: Int
)