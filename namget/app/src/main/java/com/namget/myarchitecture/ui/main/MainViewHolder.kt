package com.namget.myarchitecture.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.source.local.entity.RepoItemEntity

/**
 * Created by Namget on 2019.10.25.
 */
class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val repositoryImage: ImageView = view.findViewById(R.id.repositoryImage)
    private val repositoryName: TextView = view.findViewById(R.id.repositoryName)
    private val repositoryLanguage: TextView = view.findViewById(R.id.repositoryLanguage)


    fun bind(repoItemEntity: RepoItemEntity) {
        repositoryImage.load(repoItemEntity.avatarUrl)
        repositoryName.text = repoItemEntity.fullName
        repositoryLanguage.text = repoItemEntity.language
    }
}