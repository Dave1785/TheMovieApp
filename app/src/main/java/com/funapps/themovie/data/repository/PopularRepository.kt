package com.funapps.themovie.data.repository

import com.funapps.themovie.data.database.PopularDao
import com.funapps.themovie.network.NetworkState
import com.funapps.themovie.network.remoteSource.PopularRemoteSource
import com.funapps.themovie.network.performGetOperation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularRepository @Inject constructor(
    private val popularRemoteSource: PopularRemoteSource,
    private val popularDao: PopularDao,
    private val networkState: NetworkState,
) {

    suspend fun getPopularList() = performGetOperation(
        networkState = networkState,
        databaseQuery = { popularDao.getAllPopular() },
        networkCall = { popularRemoteSource.getMostPopularList() },
        saveCallResult = { it?.let { popularList -> popularDao.insertOrUpdate(popularList[0]) } },
    )

}