package com.funapps.themovie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.funapps.themovie.receivers.WifiBroadcastReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheMovieDBActivity : AppCompatActivity(){

    private lateinit var navController: NavController
    private val broadcastReceiver = WifiBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)

        val actionBar = supportActionBar

        // Set the background color
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#590eed")))

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        navController.navigate(R.id.profileFragment)
        actionBar?.title = "The most popular"

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuProfile -> {
                    actionBar?.title = "The most popular"
                    navController.navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menuMovies -> {
                    actionBar?.title = "The movies"
                    navController.navigate(R.id.moviesFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menuMaps -> {
                    actionBar?.title = "The map"
                    navController.navigate(R.id.mapsFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menuCamera -> {
                    actionBar?.title = "Upload File"
                    navController.navigate(R.id.cameraFragment)
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

        createNotificationChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "LOCATION_CHANNEL_ID"
            val descriptionText = "Location notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("LOCATION_CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

