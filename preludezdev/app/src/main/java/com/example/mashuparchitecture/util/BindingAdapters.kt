package com.example.mashuparchitecture.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mashuparchitecture.R

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (url.isNullOrEmpty()) return

    Glide
        .with(this.rootView)
        .load(url)
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .into(this)
}