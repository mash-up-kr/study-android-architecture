package com.tistory.mashuparchitecture.presentation.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tistory.mashuparchitecture.R
import com.tistory.mashuparchitecture.model.RepoItem
import com.tistory.mashuparchitecture.presentation.repo.RepositoryActivity
import kotlinx.android.synthetic.main.item_repository.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.RepositoryHolder>() {

    private var items: MutableList<RepoItem> = mutableListOf()

    private val placeholder = ColorDrawable(Color.GRAY)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepositoryHolder(parent).apply {
            itemView.setOnClickListener {

                val item = items[adapterPosition]
                RepositoryActivity.startRepositoryActivity(
                    it.context,
                    item.owner.ownerName,
                    item.repoName
                )
            }
        }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        items[position].let { repo ->
            with(holder.itemView) {
                Glide.with(context)
                    .load(repo.owner.ownerUrl)
                    .placeholder(placeholder)
                    .into(ivItemRepositoryProfile)

                tvItemRepositoryTitle.text = repo.title
                tvItemRepositoryLanguage.text = if (TextUtils.isEmpty(repo.language))
                    context.getText(R.string.no_language_specified)
                else
                    repo.language
            }
        }
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<RepoItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class RepositoryHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
    )
}