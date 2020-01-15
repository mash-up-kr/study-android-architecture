package com.namget.myarchitecture.ui.search

import com.namget.myarchitecture.data.response.RepoListResponse
import com.namget.myarchitecture.databinding.ItemSearchBinding
import com.namget.myarchitecture.ui.base.BaseViewHolder

/**
 * Created by Namget on 2019.10.22.
 */
class SearchViewHolder(
    private val binding: ItemSearchBinding,
    private val selectedCallback: (Int) -> Unit
) :
    BaseViewHolder<RepoListResponse.RepoItem>(binding) {
    init {
        binding.root.setOnClickListener {
            selectedCallback.invoke(adapterPosition)
        }
    }


    override fun bind(repoItem: RepoListResponse.RepoItem) {
        binding.repoEntitiy = repoItem.toRepoEntity()
    }

}