package com.runeanim.mytoyproject

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("app:userImage")
fun setUserImage(imageView: ImageView, path: String?) {
    if (path.isNullOrEmpty())
        return

    Glide.with(imageView.context)
        .load(path)
        .into(imageView)
}
