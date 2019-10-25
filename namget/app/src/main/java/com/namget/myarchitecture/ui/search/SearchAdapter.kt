package com.namget.myarchitecture.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.response.RepoListResponse

/**
 * Created by Namget on 2019.10.22.
 */
class SearchAdapter(
    diffCallback: DiffUtil.ItemCallback<RepoListResponse.RepoItem>,
    private val selectedCallback: (Int) -> Unit
) :
    ListAdapter<RepoListResponse.RepoItem, SearchViewHolder>(diffCallback) {

    fun getAdapterItem(position: Int) : RepoListResponse.RepoItem{
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(inflater.rootView, selectedCallback)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}