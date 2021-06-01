package com.runeanim.mytoyproject.ui.detail

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.base.BaseFragment
import com.runeanim.mytoyproject.databinding.DetailFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailFragment : BaseFragment<DetailFragmentBinding, DetailViewModel>(R.layout.detail_fragment) {

    private val args: DetailFragmentArgs by navArgs()

    override val viewModel: DetailViewModel by inject {
        parametersOf(
            args.repoUrl,
            args.userName
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.start()
    }
}
