package com.namget.myarchitecture.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Namget on 2019.10.25.
 */
fun Date.dateToNumberFormat(): String {
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREA)
        return dateFormat.format(this)
    } catch (e: IllegalArgumentException) {
        e("DateFormatError", "error : ", e)
    }
    return ""
}