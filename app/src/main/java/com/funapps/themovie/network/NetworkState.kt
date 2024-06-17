package com.funapps.themovie.network

sealed class NetworkState {
   data object Connected : NetworkState()
   data object Disconnected : NetworkState()
}