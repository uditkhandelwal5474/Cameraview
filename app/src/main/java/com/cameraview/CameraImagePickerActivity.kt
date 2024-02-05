package com.cameraview

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cameraview.databinding.FragmentCameraBinding
import com.cameraview_lib.CameraListener
import com.cameraview_lib.PictureResult
import com.cameraview_lib.controls.Audio
import java.io.File

//import com.facebook.react.bridge.Arguments
//import com.facebook.react.bridge.WritableMap
//import com.facebook.react.modules.core.DeviceEventManagerModule
import com.cameraview_lib.VideoResult
import com.cameraview_lib.controls.Flash

class CameraImagePickerActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var runnable: Runnable
    var quality = 0.5
    var base64 = false
    var isEmit = false
    private lateinit var binding: FragmentCameraBinding


    companion object {
        fun startCameraActivity(activity: Context, bundle: Bundle) {
            val intent = Intent(activity, CameraImagePickerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_camera)

        binding = FragmentCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quality = intent?.getDoubleExtra("quality", 0.5) ?: 0.5
        base64 = intent?.getBooleanExtra("includeBase64", false) ?: false

        binding.imageOption.visibility = View.GONE
        binding.tvCamera.visibility = View.GONE

        binding.button?.enablePhotoTaking(true)
        binding.button?.enableVideoRecording(false)
        setupCamera()
        binding.button?.actionListener = object : CameraVideoButton.ActionListener {
            override fun onStartRecord() {}

            override fun onEndRecord() {}

            override fun onDurationTooShortError() {
            }

            override fun onSingleTap() {
                binding.camera?.takePicture()
            }
        }
        binding.cameraClose.setOnClickListener(this)
        binding.toggleCameraView.setOnClickListener(this)
        binding.cameraFlash.setOnClickListener(this)


    }

    private fun setupCamera() {
        binding.camera?.audio = Audio.OFF
        binding.camera?.setLifecycleOwner(this)
        binding.camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                val pictureFile = getOutputFile(".jpeg", true)
                result.toFile(pictureFile) { file ->
                    val imageCompressed =
                        getCompressedImageFile(file?.absolutePath, quality.times(100).toInt())

                    val fileUri = Uri.fromFile(file)

                    Log.e("onPictureTaken: ", fileUri.path.toString())
                    Log.e("onPictureTaken: ", fileUri.toString())

//                    val imageMap = getImageResponseMap(fileUri)

//                    if (base64) {
//                        imageMap?.putString("base64", convertInToBase64(imageCompressed))
//                    }

//                    val assets = Arguments.createArray()
//                    assets.pushMap(imageMap)
//                    val response = Arguments.createMap()
//                    response.putArray("assets", assets)
//                    if (!isEmit) {
//                        isEmit = true
//                        MainApplication.getInstance().reactNativeHost.reactInstanceManager.currentReactContext
//                                ?.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
//                                ?.emit("ImageCapture", response)
//                    }


//                    val reactContext = MainApplication.instance.reactNativeHost.reactInstanceManager.currentReactContext
//                    if (reactContext == null) {
//                        Log.e("MyTag", "ReactContext is null")
//                        return
//                    }
//                    val appContext = reactContext.applicationContext


                    finish()
                }
            }

            override fun onVideoTaken(result: VideoResult) {}

            override fun onVideoRecordingStart() {}
            override fun onVideoRecordingEnd() {}

        })
    }

//    fun getImageResponseMap(uri: Uri): WritableMap {
//        val fileName = uri.lastPathSegment
//        val map = Arguments.createMap()
//        map.putString("uri", uri.toString())
//        map.putDouble(
//            "fileSize", File(uri.path).length().toDouble()
//        )
//        map.putString("fileName", fileName)
//        map.putString("type", "image/jpeg")
//        map?.putInt("width", uri.getImageDimension()?.outWidth ?: 0)
//        map?.putInt("height", uri.getImageDimension()?.outHeight ?: 0)
//        return map
//    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.camera_close -> {
                onBackPressed()
            }

            R.id.toggleCameraView -> {
                if (!binding.camera?.isTakingPicture!!) {
                    binding.camera?.toggleFacing()
                }
            }

            R.id.camera_flash -> {
                when (binding.camera?.flash) {
                    Flash.OFF -> {
                        binding.camera?.flash = Flash.ON
                        binding.cameraFlash?.setImageResource(R.drawable.ic_baseline_flash_on)
                    }

                    Flash.ON -> {
                        binding.camera?.flash = Flash.AUTO
                        binding.cameraFlash?.setImageResource(R.drawable.ic_baseline_flash_auto)
                    }

                    else -> {
                        binding.camera?.flash = Flash.OFF
                        binding.cameraFlash?.setImageResource(R.drawable.ic_baseline_flash_off)
                    }
                }
            }
        }
    }

    override fun onResume() {
        runOnUiThread { binding.camera?.open() }
        super.onResume()
    }

    override fun onPause() {
        binding.camera?.close()
        super.onPause()
    }

    override fun onDestroy() {
        binding.camera?.clearCameraListeners()
        binding.camera?.destroy()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            if (ActivityCompat.checkSelfPermission(
                    this@CameraImagePickerActivity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
                setupCamera()
            else
                finish()

        }
    }
}