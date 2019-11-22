package com.example.mashuparchitecture.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseViewHolder
import com.example.mashuparchitecture.data.source.vo.GithubRepoEntity
import com.example.mashuparchitecture.databinding.RepoItemLayoutBinding

class MainRvAdapter : RecyclerView.Adapter<MainRvAdapter.MainRvViewHolder>() {
    private val data = mutableListOf<GithubRepoEntity>()

    fun setData(newData: List<GithubRepoEntity>?) {
        if (newData != null) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvViewHolder =
        MainRvViewHolder(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MainRvViewHolder, position: Int) =
        holder.bind(data[position])


    class MainRvViewHolder(parent: ViewGroup) :
        BaseViewHolder<RepoItemLayoutBinding>(R.layout.repo_item_layout, parent) {

        fun bind(item: GithubRepoEntity) {
            binding.repo = item
        }
    }
}