package miinjung.study.test

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail2.*
import java.net.URL

class DetailActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Log.i("msg","detail")
        setContentView(R.layout.activity_detail)
        val intent = intent

        user_full_name.text = intent.getStringExtra("fullName")
        language.text = intent.getStringExtra("language")
        description.text = intent.getStringExtra("description")
        update_at.text = intent.getStringExtra("update")
        stars.text = "★ " + intent.getIntExtra("star",0) + "  stars"

        Glide.with(this).load(intent.getStringExtra("image")).into(user_image)


        super.onCreate(savedInstanceState, persistentState)
    }

}
