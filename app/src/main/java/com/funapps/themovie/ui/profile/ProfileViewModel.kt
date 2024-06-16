package com.funapps.themovie.ui.profile

import androidx.lifecycle.ViewModel
import com.funapps.themovie.data.repository.PopularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: PopularRepository) : ViewModel() {
}