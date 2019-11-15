package miinjung.study.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import miinjung.study.test.model.Item
import miinjung.study.test.network.TestApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {

        const val KEY_USER_LOGIN = "ownerLogin"

        const val KEY_REPO_NAME = "name"
    }

    private val api by lazy { TestApplication.getInstance().buildServerInterface() }
    private var apiCall:Call<Item>? = null
    lateinit var name : String
    lateinit var ownerLogin : String

    internal val dateFormatInResponse = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())

    internal val dateFormatToShow = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        name = intent.getStringExtra(KEY_REPO_NAME)
        ownerLogin = intent.getStringExtra(KEY_USER_LOGIN)

        searchRepos(name,ownerLogin)
    }

    fun searchRepos(name: String, ownerLogin : String){
        showProgress()

        apiCall = api?.getRepository(ownerLogin,name)
        apiCall?.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                var data = response.body()
                hideProgress()

                if (response.isSuccessful && data != null) {
                    data.let{
                        Glide.with(this@DetailActivity).load(it.owner?.avatarUrl).into(userImage)
                        userFullName.text = it.fullName
                        stars.text = "★ " + it.stargazersCount + "  stars"

                        if(it.language.isNullOrEmpty())
                            language.setText(R.string.nonLang)
                        else
                            language.text = it.language

                        if(it.description.isNullOrEmpty())
                            description.setText(R.string.nonDesc)
                        else
                            description.text =it.description

                        val date = dateFormatInResponse.parse(it.updatedAt)
                        updateAt.text = dateFormatToShow.format(date)

                    }
                } else {
                    Toast.makeText(this@DetailActivity,"Unexpected Error",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.i("errer","error")
            }
        })
    }

    private fun showProgress() {
        pbActivityRepository.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pbActivityRepository.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        apiCall?.run { cancel() }
    }

}
