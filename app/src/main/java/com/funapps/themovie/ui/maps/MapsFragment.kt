package com.funapps.themovie.ui.maps

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.funapps.themovie.R
import com.funapps.themovie.maps.LocationData
import com.funapps.themovie.maps.LocationsAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import android.location.Location
import com.funapps.themovie.network.scheduleNetworkStateCheck
import com.google.android.gms.location.LocationServices

const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationsAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var locationList: MutableList<LocationData>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, POST_NOTIFICATIONS),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        scheduleNetworkStateCheck(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)
        recyclerView = view.findViewById(R.id.maps_rv)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        db = FirebaseFirestore.getInstance()
        locationList = mutableListOf()
        adapter = LocationsAdapter(emptyList())

        recyclerView.setLayoutManager(LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);

        fetchLocationsFromFirestore()

    }

    private fun fetchLocationsFromFirestore() {
        db.collection("locations")
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val name = document.getString("name")
                        val lat = document.getDouble("latitude")
                        val lng = document.getDouble("longitude")
                        val date: Date? = document.getDate("timestamp")
                        val locationData =
                            LocationData(name ?: "", lat ?: 0.0, lng ?: 0.0, date ?: Date())
                        locationList.add(locationData)
                        val latLng = LatLng(lat ?: 0.0, lng ?: 0.0)
                        googleMap.addMarker(
                            MarkerOptions().position(latLng).title(name)
                                .snippet("TimeStamp: ${date.toString()}}")
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.w("", "Error getting documents.", task.exception)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap;
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}