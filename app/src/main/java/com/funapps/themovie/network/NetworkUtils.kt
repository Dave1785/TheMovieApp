import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.core.view.isVisible
import com.funapps.themovie.network.NetworkState
import com.google.firebase.firestore.FirebaseFirestore

object NetworkUtils {
    fun getNetworkState(context: Context): NetworkState {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return NetworkState.Disconnected
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return NetworkState.Disconnected

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkState.Connected
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkState.Connected
                else -> NetworkState.Disconnected
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return NetworkState.Disconnected
            @Suppress("DEPRECATION")
            return when (networkInfo.type) {
                ConnectivityManager.TYPE_WIFI,
                ConnectivityManager.TYPE_MOBILE -> NetworkState.Connected
                else -> NetworkState.Disconnected
            }
        }
    }


}