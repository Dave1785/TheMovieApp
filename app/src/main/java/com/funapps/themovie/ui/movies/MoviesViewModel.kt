package com.funapps.themovie.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapps.themovie.data.model.MovieResponse
import com.funapps.themovie.data.repository.MoviesRepository
import com.funapps.themovie.network.ResultType
import com.funapps.themovie.network.SortedByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _moviesList = MutableStateFlow<ResultType<MovieResponse>>(
        ResultType.Error(null)
    )
    val moviesList: StateFlow<ResultType<MovieResponse>> = _moviesList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var page = 0

    init {
        viewModelScope.launch {
            getMoviesList()
        }
    }

    fun getMoviesList(sortedByType: SortedByType = SortedByType.MOST_POPULAR) {

        if (!_isLoading.value) {
            _isLoading.value = true
            page++
            viewModelScope.launch {
                repository.getMoviesList(page, sortedByType)
                    .catch { e -> ResultType.Error(e) }
                    .collect { result ->
                        _isLoading.value = false
                        _moviesList.value = result
                    }
                delay(5000)
            }
        }

    }
}