package com.runeanim.mytoyproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {
    lateinit var viewDataBinding: T
    private val job = SupervisorJob()
    val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding.lifecycleOwner = this@BaseFragment.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
