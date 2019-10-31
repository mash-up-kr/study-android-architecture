package com.runeanim.mytoyproject.search

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.runeanim.mytoyproject.data.model.Repository

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Repository>) {
    (listView.adapter as SearchAdapter).submitList(items)
}

@BindingAdapter("app:userImage")
fun setUserImage(imageView: ImageView, path: String) {
    Glide.with(imageView.context)
        .load(path)
        .into(imageView)
}

@BindingAdapter("app:setOnQueryTextListener")
fun setOnQueryTextListener(searchView: SearchView, searchByKeyWord: (String) -> Unit) {
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
