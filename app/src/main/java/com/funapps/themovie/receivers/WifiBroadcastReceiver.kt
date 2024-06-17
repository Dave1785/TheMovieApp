package com.funapps.themovie.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class WifiBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = networkInfo != null && networkInfo.isConnectedOrConnecting

        if (isConnected) {
            Toast.makeText(context, "WiFi Connected", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(context, "WiFi Disconnected", Toast.LENGTH_LONG).show()
        }
    }

}
