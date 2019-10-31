package com.runeanim.mytoyproject

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.base.RepoListAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<RepositoryEntity>) {
    (listView.adapter as RepoListAdapter).submitList(items)
}

@BindingAdapter("app:userImage")
fun setUserImage(imageView: ImageView, path: String?) {
    if (path.isNullOrEmpty())
        return

    Glide.with(imageView.context)
        .load(path)
        .into(imageView)
}
