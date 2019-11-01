package com.tistory.mashuparchitecture.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.presentation.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnActivityMainSearch.setOnClickListener {
            startActivity<SearchActivity>()
        }

    }
}
