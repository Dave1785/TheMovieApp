package com.funapps.themovie.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.usecases.GetPopularList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getPopularList: GetPopularList) :
    ViewModel() {

    private val _popularList = MutableStateFlow<Popular?>(null)
    val popularList: StateFlow<Popular?> = _popularList

    init {
        getPopularList(1)
    }

    private fun getPopularList(page: Int) {
        viewModelScope.launch {
            getPopularList.getMostPopular(1).collect {
                _popularList.value = it
            }
        }
    }
}