package com.example.mashuparchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.databinding.ActivityMainBinding
import com.example.mashuparchitecture.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initEvent()
    }

    private fun initEvent() {
        binding.fabSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}
