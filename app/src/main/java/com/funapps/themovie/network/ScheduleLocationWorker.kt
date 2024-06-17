package com.funapps.themovie.network

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleNetworkStateCheck(context: Context) {

    val periodicWorkRequest = PeriodicWorkRequestBuilder<LocationStateWorker>(5, TimeUnit.MINUTES)
        .build()

    // Enqueue the periodic work
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "LocationWorker",
        ExistingPeriodicWorkPolicy.REPLACE,
        periodicWorkRequest
    )
}