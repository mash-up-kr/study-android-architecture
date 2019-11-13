package com.namget.myarchitecture.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Namget on 2019.10.23.
 */
fun Context.showKeyboard() {
    GlobalScope.launch {
        delay(100)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }
}

fun Context.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.HIDE_IMPLICIT_ONLY,
        0
    )
}


//Toast
fun Context.showToast(text: String, length : Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, length).show()

//Toast
fun Context.showToast(res: Int, length : Int = Toast.LENGTH_SHORT) = Toast.makeText(this, res, length).show()