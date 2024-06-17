package com.funapps.themovie.network

import com.google.firebase.firestore.FirebaseFirestore

fun addLocation(
    database: FirebaseFirestore,
    collection: String,
    name: String,
    latitude: Double?,
    longitude: Double?,
    successCallBack: (isSucess: Boolean, id: String, e: Exception?) -> Unit
) {
    // Create a new location data
    val location = hashMapOf(
        "name" to name,
        "latitude" to latitude,
        "longitude" to longitude,
        "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()
    )

    // Add a new document with a generated ID
    database.collection(collection)
        .add(location)
        .addOnSuccessListener { documentReference ->
            successCallBack(true, documentReference.id, null)
        }
        .addOnFailureListener { e ->
            successCallBack(false, "", e)
        }
}