package miinjung.study.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail2.*
import org.w3c.dom.Text

class Detail2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail2)
        val intent = intent

//
//        val user : TextView = .findView
//        var item : item = intent.extras!!.get("item") as item

        intent.let {
            Log.i("data",it.getStringExtra("fullName"))
            user_full_name.text = it.getStringExtra("fullName")
            if(it.getStringExtra("language") != " ")
                language.text = it.getStringExtra("language")
            description.text = it.getStringExtra("description")
            update_at.text = it.getStringExtra("update")
            stars.text = "★ " + it.getIntExtra("star",0) + "  stars"
            Glide.with(this).load(intent.getStringExtra("image")).into(user_image)
        }

//
//        item?.let{
//            if(it.language != "")
//                language!!.text = it.language
//            user_full_name!!.text = it.full_name
//            description!!.text = it.description
//            update_at!!.text = it.updated_at
//            stars!!.text = "★ " + it.stargazers_count + "  stars"
//
//            Glide.with(this).load(it.owner?.avatar_url)!!.into(user_image)
//        }

//        if(item.language != "")
//            language!!.text = item.language
//        user_full_name!!.text = item.full_name
//        description!!.text = item.description
//        update_at!!.text = item.updated_at
//        stars!!.text = "★ " + item.stargazers_count + "  stars"
//
//        Glide.with(this).load(item.owner?.avatar_url)!!.into(user_image)



    }
}
