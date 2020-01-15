package com.namget.myarchitecture.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.databinding.ItemSearchBinding

/**
 * Created by Namget on 2019.10.25.
 */
class MainAdapter(private val list: MutableList<RepoItemEntity>) :
    RecyclerView.Adapter<MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DataBindingUtil.inflate<ItemSearchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search,
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    fun replaceItems(replaceList: List<RepoItemEntity>) {
        list.clear()
        list.addAll(replaceList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }
}