package com.funapps.themovie.usecases

import com.funapps.themovie.data.model.Movie
import com.funapps.themovie.data.repository.MoviesRepository
import com.funapps.themovie.data.repository.MoviesRepositoryImpl
import com.funapps.themovie.network.SortedByType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovies @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchAllMovies(page: Int, sortedByType: SortedByType): Flow<List<Movie>> = flow {
        val response = moviesRepository.getMoviesList(page, sortedByType)
        response?.let {
            emit(it)
        }
    }

}