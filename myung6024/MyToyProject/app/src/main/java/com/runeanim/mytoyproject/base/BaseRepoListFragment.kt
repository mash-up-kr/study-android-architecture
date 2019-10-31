package com.runeanim.mytoyproject.base

import androidx.fragment.app.Fragment
import com.runeanim.mytoyproject.data.source.local.entity.RepositoryEntity

abstract class BaseRepoListFragment : Fragment() {

    abstract fun onClickRepositoryItem(repositoryEntity: RepositoryEntity)
}
