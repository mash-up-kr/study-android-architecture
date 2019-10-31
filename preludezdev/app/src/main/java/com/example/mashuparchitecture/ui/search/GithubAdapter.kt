package com.example.mashuparchitecture.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mashuparchitecture.R
import com.example.mashuparchitecture.databinding.RepoItemLayoutBinding
import com.example.mashuparchitecture.network.vo.GithubRepositoriesResponse
import com.example.mashuparchitecture.ui.detail.DetailActivity

class GithubAdapter(private val context: Context) :
    RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
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

    inner class GithubViewHolder(private val binding: RepoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubRepositoriesResponse.Item) {
            with(binding) {
                tvRepoName.text = item.fullName
                tvLanguage.text = item.language

                root.setOnClickListener {
                    context.startActivity(
                        Intent(
                            context, DetailActivity::class.java
                        ).apply { putExtra("item", item) })
                }
            }

            Glide
                .with(binding.root)
                .load(item.owner.avatarUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.ivAvatar)
        }
    }
}