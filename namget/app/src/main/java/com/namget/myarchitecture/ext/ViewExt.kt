package com.namget.myarchitecture.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Namget on 2019.10.23.
 */
fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, text, length).show()

fun View.showSnackBar(res: Int, length: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, res, length).show()

fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}