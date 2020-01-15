package com.namget.myarchitecture.ui.main

import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity
import com.namget.myarchitecture.databinding.ItemSearchBinding
import com.namget.myarchitecture.ui.base.BaseViewHolder

/**
 * Created by Namget on 2019.10.25.
 */
class MainViewHolder(private val binding: ItemSearchBinding) :
    BaseViewHolder<RepoItemEntity>(binding) {


    override fun bind(repoItemEntity: RepoItemEntity) {
        binding.repoEntitiy = repoItemEntity
    }
}