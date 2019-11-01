package com.runeanim.mytoyproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity
import com.runeanim.mytoyproject.databinding.RepoItemBinding

class RepoListAdapter(private val listener: RepoItemClickListener) :
        ListAdapter<RepositoryEntity, RepoListAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: RepoItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: RepoItemClickListener, item: RepositoryEntity) {

            binding.listener = listener
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback : DiffUtil.ItemCallback<RepositoryEntity>() {
    override fun areItemsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean {
        return oldItem.order == newItem.order
    }

    override fun areContentsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean {
        return oldItem == newItem
    }
}
