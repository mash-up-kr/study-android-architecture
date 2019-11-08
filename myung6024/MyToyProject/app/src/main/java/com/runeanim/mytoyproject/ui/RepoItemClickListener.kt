package com.runeanim.mytoyproject.ui

import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

interface RepoItemClickListener {

    fun onClickRepositoryItem(repositoryEntity: RepositoryEntity)
}
