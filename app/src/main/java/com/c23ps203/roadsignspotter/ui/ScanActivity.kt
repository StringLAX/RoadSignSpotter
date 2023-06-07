package com.c23ps203.roadsignspotter.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.data.api.Api
import com.c23ps203.roadsignspotter.data.api.RetroScan
import com.c23ps203.roadsignspotter.data.model.response.ScanResponse
import com.c23ps203.roadsignspotter.databinding.ActivityScanBinding
import com.c23ps203.roadsignspotter.utils.rotateBitmap
import com.c23ps203.roadsignspotter.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, R.string.no_permission, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS_CAMERA.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Scan"

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS_CAMERA,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.ivScanPreview.setOnClickListener {
            startCamera()
        }

        binding.btnGetFromGallery.setOnClickListener {
            startGallery()
        }

        binding.btnScannow.setOnClickListener {
            startScan()
        }
    }

    private var getFile: File? = null

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCamera.launch(intent)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                binding.ivScanPreview.setImageURI(Uri.fromFile(myFile))
            } else {
                val result = rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )
                binding.ivScanPreview.background = null
                binding.ivScanPreview.setImageBitmap(result)
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            contentResolver
            val myFile = uriToFile(selectedImg, this@ScanActivity)

            getFile = myFile

            binding.ivScanPreview.background = null
            binding.ivScanPreview.setImageURI(selectedImg)
        }
    }

    private fun startScan() {
        showLoading(true)
        if (getFile != null) {
            val file = getFile as File

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val retro = RetroScan().getRetroClientInstance().create(Api::class.java)
            retro.scanImage(imageMultipart).enqueue(object : Callback<ScanResponse> {
                override fun onResponse(
                    call: Call<ScanResponse>,
                    response: Response<ScanResponse>
                ) {
                    if (response.isSuccessful) {
                        showLoading(false)
                        val responseBody = response.body()
                        Log.d("TAG", "onResponse: ${responseBody?.label}")
                        Log.d("TAG", "onResponse: ${responseBody?.confidence}")
                        val imageUri = Uri.fromFile(file)
                        intent = Intent(this@ScanActivity, ResultActivity::class.java).also {
                            it.putExtra(ResultActivity.EXTRA_LABEL, responseBody?.label)
                            it.putExtra(ResultActivity.EXTRA_CONFIDENCE, responseBody?.confidence)
                            it.putExtra(ResultActivity.EXTRA_IMAGE, imageUri)
                        }

                        Log.d("TAG", "onResponse: ${responseBody?.label}")
                        Log.d("TAG", "onResponse: ${responseBody?.confidence}")
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@ScanActivity, "gagal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(
                        this@ScanActivity,
                        resources.getString(R.string.image_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        } else {
            showLoading(false)
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "startScan: image empty")
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}