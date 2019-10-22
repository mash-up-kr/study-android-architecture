package com.namget.myarchitecture.ui.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.namget.myarchitecture.R
import com.namget.myarchitecture.data.api.response.UserListResponse

/**
 * Created by Namget on 2019.10.22.
 */
class SearchViewHolder(val view : View)  : RecyclerView.ViewHolder(view){
    val repositoryImage : ImageView = view.findViewById(R.id.repositoryImage)
    val repositoryName : TextView = view.findViewById(R.id.repositoryName)
    val repositoryLanguage : TextView = view.findViewById(R.id.repositoryLanguage)

    fun bind(repositoryItem: UserListResponse.RepositoryItem){
        //repo Image Url
        repositoryImage.load(repositoryItem.url)
        repositoryName.text = repositoryItem.fullName
        repositoryLanguage.text = repositoryItem.language


    }

}