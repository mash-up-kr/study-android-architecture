package com.runeanim.mytoyproject.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.runeanim.mytoyproject.R
import com.runeanim.mytoyproject.data.Result.Success
import com.runeanim.mytoyproject.domain.SearchRepositoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private val searchRepositoriesUseCase: SearchRepositoriesUseCase by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchRepositoryByKeyWord("myung6024")
    }

    private fun searchRepositoryByKeyWord(searchKeyWord: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = searchRepositoriesUseCase(searchKeyWord)
            if (result is Success) {
                Log.d("loloss", result.data.toString())
            } else {
                Log.d("loloss", "fail")
            }
        }
    }
}
