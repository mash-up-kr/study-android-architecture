package miinjung.study.test.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import miinjung.study.test.R
import miinjung.study.test.model.Item
import miinjung.study.test.network.TestApplication
import miinjung.study.test.util.KeyName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity(),DetailContract.View {

    private lateinit var presenter: DetailPresenter

    private lateinit var name : String
    private lateinit var ownerLogin : String

    private val dateFormatInResponse = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())

    private val dateFormatToShow = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter = DetailPresenter(this,this)
        getKey()

        presenter.searchRepos(ownerLogin,name)
    }

    override fun getKey(){

        val intent = intent

        intent.getStringExtra(KeyName.KEY_REPO_NAME)?.let{
            name = it
        }
        intent.getStringExtra(KeyName.KEY_USER_LOGIN)?.let {
            ownerLogin = it
        }
    }

    override fun dataBind(item: Item) {
        Glide.with(this).load(item.owner?.avatarUrl).into(userImage)
        userFullName.setText(item.fullName)
        stars.setText("★ ${item.stargazersCount} stars")

        if(item.language.isNullOrEmpty())
            language.setText(R.string.nonLang)
        else
            language.text = item.language

        if(item.description.isNullOrEmpty())
            description.setText(R.string.nonDesc)
        else
            description.text =item.description

        val date = dateFormatInResponse.parse(item.updatedAt)
        updateAt.text = dateFormatToShow.format(date)
    }

    override fun showProgress() {
        pbActivityRepository.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbActivityRepository.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        presenter.apiStop()
    }


}
