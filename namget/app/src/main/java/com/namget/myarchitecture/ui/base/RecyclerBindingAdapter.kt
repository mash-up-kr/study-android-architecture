package com.namget.myarchitecture.ui.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.ui.main.MainAdapter
import com.namget.myarchitecture.ui.search.SearchAdapter

/**
 * Created by Namget on 2019.12.13.
 */

@BindingAdapter("android:repoList")
fun RecyclerView.setList(list: List<RepoItemEntity>) {
    (this.adapter as? MainAdapter).run {
        this?.replaceItems(list)
    }
}

@BindingAdapter("android:repItemList")
fun RecyclerView.submitList(list: List<RepoListResponse.RepoItem>) {
    (this.adapter as? SearchAdapter).run {
        this?.submitList(list)
    }
}