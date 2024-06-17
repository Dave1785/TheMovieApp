package com.funapps.themovie.ui.home

import androidx.lifecycle.ViewModel
import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.network.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _popularList = MutableStateFlow<ResultType<PopularResponse>>(
        ResultType.Error(null)
    )
    val popularList: StateFlow<ResultType<PopularResponse>> = _popularList

    fun getPopularList(page: Int) {

    }
}