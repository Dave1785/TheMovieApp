package com.funapps.themovie.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.funapps.themovie.R
import com.funapps.themovie.network.ResultType
import com.funapps.themovie.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class MoviesFragment: Fragment()  {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED){
                homeViewModel.getPopularList(1)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.popularList.collect { result ->
                when(result){
                    is ResultType.Error -> {

                    }
                    is ResultType.Success -> {

                    }
                }
            }
        }
    }
}