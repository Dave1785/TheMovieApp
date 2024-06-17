package com.funapps.themovie.data.repository

import com.funapps.themovie.data.database.PopularDao
import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.data.remote.TheMovieApiService
import com.funapps.themovie.network.NetworkState
import com.funapps.themovie.network.PopularRemoteSource
import com.funapps.themovie.network.performGetOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularRepository @Inject constructor(
    private val popularRemoteSource: PopularRemoteSource,
    private val popularDao: PopularDao,
    private val networkState: NetworkState,
) {

    suspend fun getPopularList(page: Int) = performGetOperation(
        networkState = networkState,
        databaseQuery = { popularDao.getAllPopular() },
        networkCall = { popularRemoteSource.getMostPopularListAsync() },
        saveCallResult = { it?.let { popularList -> popularDao.insertOrUpdate(popularList[0]) } },
    )

    /* override suspend fun getPopularList(page: Int): ResultType<PopularResponse> {
         try {
             when (networkState) {
                 is NetworkState.Connected -> {
                     val response = service.getPopularList("en-US", page.toString())

                     if (response.isSuccessful) {
                         val data = response.body()
                         if (data != null) {
                             saveData(data.results[0])
                             return ResultType.Success(data)
                         } else {
                             return ResultType.Error(Exception("Empty response"))
                         }
                     } else {
                         return ResultType.Error(Exception("Network call failed"))
                     }
                 }

                 is NetworkState.Disconnected -> {
                     return ResultType.Error(Exception())
                 }
             }

         } catch (e: Exception) {
             return ResultType.Error(e)
         }
     }*/


}