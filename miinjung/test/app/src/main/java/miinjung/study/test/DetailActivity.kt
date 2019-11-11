package miinjung.study.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.user_image
import kotlinx.android.synthetic.main.fragment_serch.*
import miinjung.study.test.Model.item
import miinjung.study.test.network.Controller
import miinjung.study.test.network.ServerInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    val api : ServerInterface? by lazy { Controller.getInstance().buildServerInterface() }
    internal var apiCall:Call<item>? = null
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
        name = intent.getStringExtra("name")
        ownerLogin = intent.getStringExtra("ownerLogin")

        searchRepos(name,ownerLogin)
    }

    fun searchRepos(name: String, ownerLogin : String){
        apiCall = api?.getRepository(ownerLogin,name)
        apiCall?.enqueue(object : Callback<item> {
            override fun onResponse(call: Call<item>, response: Response<item>) {
                var data = response.body()

                if (response.isSuccessful && data != null) {
                    data.let{
                        Glide.with(this@DetailActivity).load(it.owner?.avatar_url).into(user_image)
                        user_full_name.text = it.full_name
                        stars.text = "★ " + it.stargazers_count + "  stars"

                        if(it.language.equals(null))
                            language.setText(R.string.nonLang)
                        else
                            language.text = it.language

                        if(it.description.equals(null))
                            description.setText(R.string.nonDesc)
                        else
                            description.text =it.description

                        val date = dateFormatInResponse.parse(it.updated_at)
                        update_at.text = dateFormatToShow.format(date)

                    }
                } else {
                    Toast.makeText(this@DetailActivity,"Unexpected Error",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<item>, t: Throwable) {
                Log.i("errer","error")
            }
        })
    }


}
