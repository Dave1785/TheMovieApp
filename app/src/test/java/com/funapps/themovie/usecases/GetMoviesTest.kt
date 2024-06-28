package com.funapps.themovie.usecases

import app.cash.turbine.test
import com.funapps.themovie.data.model.Movie
import com.funapps.themovie.data.repository.MoviesRepository
import com.funapps.themovie.network.SortedByType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetMoviesTest {

    private lateinit var repository: MoviesRepository
    private lateinit var getMovies: GetMovies

    private val movie =
        Movie(
            id = 1.1,
            originalTitle = "MovieTest",
            overview = "Overview test",
            posterPath = "Poster test",
            title = "titleTest",
            voteAverage = 12f
        )

    @Before
    fun setUp() {
        repository = mock(MoviesRepository::class.java)
        getMovies = GetMovies(repository)
    }

    @Test
    fun `when fetchAllMovies is called return movies list`() = runTest {

        //Given
        `when`(repository.getMoviesList(1, SortedByType.MOST_RECOMMENDED)).thenReturn(listOf(movie))

        //when
        val result = getMovies.fetchAllMovies(1, SortedByType.MOST_RECOMMENDED)

        //then
        result.test {
            val movieList = expectMostRecentItem()
            assert(movieList.isNotEmpty())
            assert(movieList[0].id == 1.1)
        }

    }

    @Test
    fun `when fetchAllMovies for first time is called and wifi is disconnected return movies list must be empty`() = runTest {

        //Given
        `when`(repository.getMoviesList(1, SortedByType.MOST_RECOMMENDED)).thenReturn(emptyList())

        //when
        val result = getMovies.fetchAllMovies(1, SortedByType.MOST_RECOMMENDED)

        //then
        result.test {
            val movieList = expectMostRecentItem()
            assert(movieList.isEmpty())
        }

    }

}