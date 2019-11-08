package miinjung.study.test.Model

import java.io.Serializable

class item :Serializable {
    var name: String? = null
    var full_name: String? = null
    var owner: owner? = null
    var description: String? = null
    var language: String? = null
    var updated_at: String? = null
    var stargazers_count: Int? = null
}