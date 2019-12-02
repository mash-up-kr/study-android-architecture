package com.namget.myarchitecture.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity

/**
 * Created by Namget on 2019.12.02.
 */

@BindingAdapter("android:repoList")
fun RecyclerView.setList(list : List<RepoItemEntity>){
    (this.adapter as? MainAdapter).run { this?.replaceItems(list) }
}