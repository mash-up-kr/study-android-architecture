package com.namget.myarchitecture.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Namget on 2019.10.25.
 */
fun Date?.dateToNumberFormat(): String {

    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREA)
        this?.let { dateFormat.format(it) } ?: ""
    } catch (e: IllegalArgumentException) {
        e("DateFormatError", "error : ", e)
        return ""
    }
}