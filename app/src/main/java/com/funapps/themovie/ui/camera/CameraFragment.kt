package com.funapps.themovie.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.funapps.themovie.R
import com.funapps.themovie.network.ResultType
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private val cameraViewModel: CameraViewModel by viewModels()
    private lateinit var db: FirebaseFirestore

    private lateinit var loadingView: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        val name = view.findViewById<EditText>(R.id.name_et)
        val latitudeET = view.findViewById<EditText>(R.id.latitude_et)
        val longitudeET = view.findViewById<EditText>(R.id.longitude_et)
        val addLocation = view.findViewById<Button>(R.id.add_location_bt)
        loadingView = view.findViewById(R.id.loading_view)

        addLocation.setOnClickListener {
            loadingView.isVisible = true
            addLocation(
                name = name.text.toString(),
                latitude = latitudeET.text.toString().toDoubleOrNull(),
                longitude = longitudeET.text.toString().toDoubleOrNull(),
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                cameraViewModel.getPopularList(1)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            cameraViewModel.popularList.collect { result ->

            }
        }

    }

    private fun addLocation(name: String, latitude: Double?, longitude: Double?) {
        // Create a new location data
        val location = hashMapOf(
            "name" to name,
            "latitude" to latitude,
            "longitude" to longitude,
            "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )

        // Add a new document with a generated ID
        db.collection("locations")
            .add(location)
            .addOnSuccessListener { documentReference ->
                loadingView.isVisible = false
                Toast.makeText(
                    context,
                    "Ubicación agregada: ${documentReference.id}",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                loadingView.isVisible = false
                Toast.makeText(context, "Hubo un error", Toast.LENGTH_LONG).show()
            }
    }

}