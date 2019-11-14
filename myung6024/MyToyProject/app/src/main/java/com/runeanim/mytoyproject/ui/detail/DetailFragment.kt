package com.runeanim.mytoyproject.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.data.Result
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.data.model.Owner
import com.runeanim.mytoyproject.data.model.Repository
import com.runeanim.mytoyproject.databinding.DetailFragmentBinding
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailFragment : BaseFragment<DetailFragmentBinding, DetailPresenter>(R.layout.detail_fragment), DetailContract.View {
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
            presenter = this@DetailFragment.presenter
        }
    }

    override fun setDataBindingItems(repoResult: Result<Repository>, userResult: Result<Owner>) {
        if (repoResult is Success && userResult is Success) {
            viewDataBinding.repo = repoResult.data
            viewDataBinding.owner = userResult.data
        } else {
            showError()
        }
    }

    override fun showError() {
        with(tvActivityRepositoryMessage) {
            text = "Unexpected error."
            visibility = View.VISIBLE
        }
    }
}