package com.namget.myarchitecture.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Namget on 2020.01.15.
 */
abstract class BaseViewHolder<T : Any>(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(data: T)
}