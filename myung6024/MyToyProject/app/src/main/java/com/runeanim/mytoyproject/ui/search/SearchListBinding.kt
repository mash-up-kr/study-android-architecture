package com.runeanim.mytoyproject.ui.search

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setOnQueryTextListener")
fun setOnQueryTextListener(searchView: SearchView, searchByKeyWord: (String) -> Unit) {
    searchView.isIconified = false
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { keyWord ->
                searchByKeyWord(keyWord)
            }
            return false
        }
    })
}
