package com.runeanim.mytoyproject.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.databinding.DetailFragmentBinding
import com.runeanim.mytoyproject.util.SingleLiveEvent
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailFragment : BaseFragment<DetailFragmentBinding, DetailPresenter>(R.layout.detail_fragment), DetailContract.View {

    private val _dataLoading = MutableLiveData<Boolean>(true)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _repoInfo = SingleLiveEvent<Repository>()
    val repoInfo: LiveData<Repository>
        get() = _repoInfo

    private val _ownerInfo = SingleLiveEvent<Owner>()
    val ownerInfo: LiveData<Owner>
        get() = _ownerInfo

    private val args: DetailFragmentArgs by navArgs()

    override val presenter: DetailPresenter by inject {
        parametersOf(
            args.repoUrl,
            args.userName,
            this as DetailContract.View
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            view = this@DetailFragment
        }
    }

    override fun showError() {
        with(tvActivityRepositoryMessage) {
            text = "Unexpected error."
            visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        _dataLoading.value = false
    }

    override fun showProgressBar() {
        _dataLoading.value = true
    }

    override fun showRepositoryDetail(repository: Repository, owner: Owner) {
        _repoInfo.value = repository
        _ownerInfo.value = owner
    }
}