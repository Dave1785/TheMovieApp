package com.funapps.themovie

import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.funapps.themovie.receivers.WifiBroadcastReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

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
                R.id.profileFragment -> {
                    actionBar?.title = "The most popular"
                    navController.navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.moviesFragment -> {
                    actionBar?.title = "The movies"
                    navController.navigate(R.id.moviesFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.mapsFragment -> {
                    actionBar?.title = "The map"
                    navController.navigate(R.id.mapsFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.cameraFragment -> {
                    actionBar?.title = "Add new location"
                    navController.navigate(R.id.cameraFragment)
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

}

