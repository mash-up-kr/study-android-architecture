package com.runeanim.mytoyproject.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.runeanim.mytoyproject.MainActivity
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var viewDataBinding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = MainFragmentBinding.inflate(inflater, container, false).apply {
            fragment = this@MainFragment
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    fun startSearchActivity(){
        Navigation.findNavController(viewDataBinding.root).navigate(R.id.action_main_screen_to_search_screen)
    }
}
