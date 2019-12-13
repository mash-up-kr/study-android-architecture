package com.namget.myarchitecture.ui.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

/**
 * Created by Namget on 2019.12.13.
 */
@BindingAdapter("android:coilLoad")
fun ImageView.coilLoad(url : String){
    this.load(url)
}