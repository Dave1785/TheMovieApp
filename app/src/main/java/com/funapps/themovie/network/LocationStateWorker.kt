package com.funapps.themovie.network

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.TimeUnit
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.funapps.themovie.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class LocationStateWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val db = FirebaseFirestore.getInstance()
    override fun doWork(): Result {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        try {
            val location = getLastLocation()
            val timeNow = getCurrentDate()
            location?.let {
                val latitude = it.latitude
                val longitude = it.longitude
                Log.d("LocationWorker", "Current location: ($latitude, $longitude)")
                // You can save this location to a database or shared preferences
                addLocation(
                    database = db,
                    collection = "locations",
                    name = "Device location: $timeNow",
                    latitude,
                    longitude
                ) { isSuccess, id, _ ->
                    if (isSuccess) {
                        Toast.makeText(
                            context,
                            "Device Ubicación agregada",
                            Toast.LENGTH_LONG
                        ).show()

                        showNotification(
                            title = "Device Location",
                            message = "Se agrego la ubicación del dispositivo con éxito idUbicaion:$id"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LocationWorker", "Failed to get location", e)
            return Result.retry()
        }

        return Result.success()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(): Location? {
        return try {
            val task = fusedLocationClient.lastLocation
            val result = Tasks.await(task, 10, TimeUnit.SECONDS)
            result
        } catch (e: Exception) {
            Log.e("LocationWorker", "Exception: ${e.message}", e)
            null
        }
    }

    private fun showNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(applicationContext, "LOCATION_CHANNEL_ID")
            .setSmallIcon(R.drawable.add_location)  // replace with your app's icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, notification)
        }
    }


}
