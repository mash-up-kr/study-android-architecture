package com.namget.myarchitecture.ui.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.api.response.RepoListResponse

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
    private val repositoryImage: ImageView = view.findViewById(R.id.repositoryImage)
    private val repositoryName: TextView = view.findViewById(R.id.repositoryName)
    private val repositoryLanguage: TextView = view.findViewById(R.id.repositoryLanguage)


    fun bind(repoItem: RepoListResponse.RepoItem) {
        //repo Image Url
        repositoryImage.load(repoItem.owner.avatarUrl)
        repositoryName.text = repoItem.fullName
        repositoryLanguage.text = repoItem.language
    }

}