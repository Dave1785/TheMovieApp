package com.funapps.themovie.usecases

import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.data.repository.PopularRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPopularList @Inject constructor(private val popularRepository: PopularRepository) {

    suspend fun getMostPopular(page: Int): Flow<Popular?> = flow {
        val response = popularRepository.getPopularList()
        if(!response.isNullOrEmpty()){
            emit(response[0])
        }
    }
}