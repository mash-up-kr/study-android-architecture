package com.example.mashuparchitecture.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.databinding.RepoItemLayoutBinding
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse

class GithubAdapter : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    private val data = mutableListOf<GithubRepositoriesResponse.Item>()

    fun setData(items: List<GithubRepositoriesResponse.Item>?) {
        if (items != null) {
            data.clear()
            data.addAll(items)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val binding =
            RepoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GithubViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class GithubViewHolder(private val binding: RepoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubRepositoriesResponse.Item) {
            binding.tvRepoName.text = item.name
            binding.tvLanguage.text = item.language

            Glide
                .with(binding.root)
                .load(item.owner.avatarUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.ivAvatar)
        }
    }
}