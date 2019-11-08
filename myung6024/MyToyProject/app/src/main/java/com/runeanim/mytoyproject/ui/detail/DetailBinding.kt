package com.runeanim.mytoyproject.ui.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("app:updatedAtText")
fun setUpdatedAtText(textView: TextView, updatedAt: Date) {
    val dateFormatToShow = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
    )

    textView.text = dateFormatToShow.format(updatedAt)
}
