package com.cameraview

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.cameraview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.onclickText.setOnClickListener {
            launchCamera()
        }
    }

    private fun launchCamera() {
        val bundle = Bundle()
        bundle.putDouble("quality", 0.6)
        bundle.putBoolean("includeBase64", false)
        askForPermission(arrayListOf(Manifest.permission.CAMERA)) {
            CameraImagePickerActivity.startCameraActivity(this@MainActivity, bundle)
        }
    }

}