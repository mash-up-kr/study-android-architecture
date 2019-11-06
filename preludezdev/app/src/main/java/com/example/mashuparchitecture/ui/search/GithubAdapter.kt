package com.example.mashuparchitecture.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.base.BaseViewHolder
import com.example.mashuparchitecture.databinding.RepoItemLayoutBinding
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse

class GithubAdapter(
    private val clickCallback: (position: Int) -> Unit
) : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    private val data = mutableListOf<GithubRepositoriesResponse.Item>()

    fun getItem(position: Int) = data[position]

    fun setData(items: List<GithubRepositoriesResponse.Item>?) {
        if (items != null) {
            data.clear()
            data.addAll(items)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder =
        GithubViewHolder(parent, clickCallback)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) =
        holder.bind(data[position])

    class GithubViewHolder(parent: ViewGroup, clickCallback: (position: Int) -> Unit) :
        BaseViewHolder<RepoItemLayoutBinding>(R.layout.repo_item_layout, parent) {

        init {
            itemView.setOnClickListener {
                clickCallback(adapterPosition)
            }
        }

        fun bind(item: GithubRepositoriesResponse.Item) {
            binding.repo = item
        }
    }

}
