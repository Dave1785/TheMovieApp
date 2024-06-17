package com.funapps.themovie.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.funapps.themovie.R
import com.funapps.themovie.network.ResultType
import com.funapps.themovie.network.SortedByType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment: Fragment()  {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesAdapter = MoviesAdapter()
        val moviesRV = view.findViewById<RecyclerView>(R.id.movies_rv)
        moviesRV.layoutManager = LinearLayoutManager(requireContext())
        moviesRV.adapter = moviesAdapter

        val loadingView = view.findViewById<FrameLayout>(R.id.loading_view)

        val mostPopularTv = view.findViewById<TextView>(R.id.most_popular)
        val mostRecommendedTv = view.findViewById<TextView>(R.id.most_recommended)
        val mostRankedTv = view.findViewById<TextView>(R.id.most_ranked)


        mostPopularTv.setOnClickListener {
            moviesAdapter.submitList(emptyList())
            moviesViewModel.getMoviesList(SortedByType.MOST_POPULAR)
        }

        mostRecommendedTv.setOnClickListener {
            moviesAdapter.submitList(emptyList())
            moviesViewModel.getMoviesList(SortedByType.MOST_RECOMENDED)
        }

        mostRankedTv.setOnClickListener {
            moviesAdapter.submitList(emptyList())
            moviesViewModel.getMoviesList(SortedByType.MOST_RANKED)
        }



        moviesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    moviesViewModel.getMoviesList()
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.moviesList.collect { movies ->
                movies?.let {

                    val newList = moviesAdapter.currentList.toMutableList()
                    newList.addAll(movies) // newItems is a list of additional items you want to add

                    moviesAdapter.submitList(newList)
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.isLoading.collect {
                loadingView.isVisible = it
            }
        }
    }
}