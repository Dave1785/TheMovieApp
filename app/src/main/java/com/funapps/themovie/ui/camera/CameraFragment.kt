package com.funapps.themovie.ui.camera

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.funapps.themovie.R
import com.funapps.themovie.extensions.loadUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

const val STORAGE_PERMISSION_CODE = 1
const val PICK_IMAGE_REQUEST = 2
const val REQUEST_IMAGE_CAPTURE = 3

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private var imageUri: Uri? = null
    private var currentPhotoUri: Uri? = null
    private lateinit var storageReference: StorageReference

    private lateinit var loadingView: FrameLayout
    private lateinit var uploadIv: ImageView
    private var sourceSelected: SourceImageSelected = SourceImageSelected.None

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storageReference = FirebaseStorage.getInstance().reference

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                STORAGE_PERMISSION_CODE
            )
        }
    }

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

        uploadIv = view.findViewById(R.id.upload_image)

        val galleryBt = view.findViewById<Button>(R.id.gallery_bt)
        val cameraBt = view.findViewById<Button>(R.id.camera_bt)
        val uploadBt = view.findViewById<Button>(R.id.upload_bt)

        galleryBt.setOnClickListener {
            sourceSelected = SourceImageSelected.GallerySelected
            openFileChooser()
        }

        cameraBt.setOnClickListener {
            sourceSelected = SourceImageSelected.CameraSelected
            dispatchTakePictureIntent()
        }

        uploadBt.setOnClickListener {
            uploadFile()
        }

        loadingView = view.findViewById(R.id.loading_view)

    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadFile() {
        loadingView.isVisible = true
        when (sourceSelected) {
            is SourceImageSelected.GallerySelected -> {
                uploadFileToFirebaseFromGallery()
            }

            is SourceImageSelected.CameraSelected -> {
                uploadFileToFirebaseFromCamera()
            }

            is SourceImageSelected.None -> Unit
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            imageUri?.let { uploadIv.loadUri(it) }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            currentPhotoUri?.let { uri ->
                uploadIv.loadUri(uri)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = createImageFile()
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.funapps.themovie.fileprovider",
                    it
                )
                currentPhotoUri = photoURI
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoUri = Uri.fromFile(this)
        }
    }

    private fun uploadFileToFirebaseFromGallery() {
        if (imageUri == null) return
        val fileReference = storageReference.child("uploads/${UUID.randomUUID()}.jpg")
        fileReference.putFile(imageUri!!)
            .addOnSuccessListener {
                loadingView.isVisible = false
                fileReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                }
                Toast.makeText(
                    context,
                    "Success Upload Image",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Failed upload fi",
                    Toast.LENGTH_LONG
                ).show()
                loadingView.isVisible = false
            }
    }

    private fun uploadFileToFirebaseFromCamera() {
        if (currentPhotoUri == null) return
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val fileRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")

        loadingView.isVisible = true
        fileRef.putFile(currentPhotoUri!!)
            .addOnSuccessListener {
                loadingView.isVisible = false
                Toast.makeText(
                    context,
                    "Success Upload Image",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                // Handle unsuccessful uploads
                loadingView.isVisible = false
                Toast.makeText(
                    context,
                    "Failed Upload Image",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    sealed class SourceImageSelected {
        data object CameraSelected : SourceImageSelected()
        data object GallerySelected : SourceImageSelected()
        data object None : SourceImageSelected()
    }

}