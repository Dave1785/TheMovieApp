package com.funapps.themovie

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.moviesFragment -> {
                    navController.navigate(R.id.moviesFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.mapsFragment -> {
                    navController.navigate(R.id.mapsFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.cameraFragment -> {
                    navController.navigate(R.id.cameraFragment)
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }

}

