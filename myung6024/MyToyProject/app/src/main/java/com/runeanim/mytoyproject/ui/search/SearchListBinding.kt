package com.runeanim.mytoyproject.ui.search

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.mytoyproject.data.model.Repository

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Repository>) {
    (listView.adapter as SearchAdapter).submitList(items)
}

@BindingAdapter("app:setOnQueryTextListener")
fun setOnQueryTextListener(searchView: SearchView, searchByKeyWord: (String) -> Unit) {
    (searchView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
    searchView.requestFocus()
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
