package com.funapps.themovie.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.data.repository.MoviesRepository
import com.funapps.themovie.data.repository.PopularRepository
import com.funapps.themovie.network.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _popularList = MutableStateFlow<ResultType<PopularResponse>>(
        ResultType.Error(null)
    )
    val popularList: StateFlow<ResultType<PopularResponse>> = _popularList

    fun getMovieList(page: Int) {
        viewModelScope.launch {
            repository.getMoviesList(page)
                .catch { e -> ResultType.Error(e) }
                .collect { result ->
                    _popularList.value = result
                }
        }
    }
}