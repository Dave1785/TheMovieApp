package com.funapps.themovie.network

import android.util.Log
import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.data.model.PopularResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun performGetOperation(
    networkState: NetworkState,
    databaseQuery: () -> List<Popular>,
    networkCall: suspend () -> Response<PopularResponse?>,
    saveCallResult: suspend (store: List<Popular>?) -> Unit
): List<Popular>? {

    return withContext(Dispatchers.IO) {
        when (networkState) {
            is NetworkState.Connected -> {
                var responseStore = networkCall.invoke()
                if (responseStore.isSuccessful) {
                    val data = responseStore.body()
                    Log.d("Info", "Se actualizo info")
                    try {
                        saveCallResult.invoke(data?.results)
                    } catch (e: Exception) {
                        Log.d("info", "Exception")
                    }
                    data?.results
                } else {
                     null
                }
            }

            is NetworkState.Disconnected -> {
                Log.d("Info", "Se accedio a la base")
                try {
                    Log.d("Info", "Se obtuvo info de la base")
                     databaseQuery.invoke()
                } catch (e: Exception) {
                    Log.d("Info", "Fallo acceso a la base ")
                     null
                }
            }
        }

    }


}