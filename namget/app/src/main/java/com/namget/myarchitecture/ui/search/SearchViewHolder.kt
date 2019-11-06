package com.namget.myarchitecture.ui.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.namget.myarchitecture.data.response.RepoListResponse
import kotlinx.android.synthetic.main.item_search.view.*

/**
 * Created by Namget on 2019.10.22.
 */
class SearchViewHolder(view: View, selectedCallback: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            selectedCallback.invoke(adapterPosition)
        }
    }

    private val repositoryImage: ImageView = view.repositoryImage
    private val repositoryName: TextView = view.repositoryName
    private val repositoryLanguage: TextView = view.repositoryLanguage


    fun bind(repoItem: RepoListResponse.RepoItem) {
        //repo Image Url
        repositoryImage.load(repoItem.owner.avatarUrl)
        repositoryName.text = repoItem.fullName
        repositoryLanguage.text =
            if (repoItem.language.isNullOrEmpty()) "No language specified" else repoItem.language
    }

}