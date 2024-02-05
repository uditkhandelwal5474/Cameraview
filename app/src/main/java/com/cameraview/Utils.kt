package com.cameraview

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.graphics.*
import android.media.*
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.text.*
import android.util.*
import android.util.Base64
import android.widget.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import java.util.*

@SuppressLint("MissingPermission")
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun showShortToast(activity: Context?, msg: String?) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
}

fun showToast(activity: Activity, msg: String?) {
    activity.runOnUiThread { Toast.makeText(activity, msg, Toast.LENGTH_LONG).show() }
}

fun Activity.askForPermission(permissions: ArrayList<String>, onSuccess: () -> Unit) {
    if (permissions.isNullOrEmpty()) return
    if (Build.VERSION.SDK_INT >= 29) {
        permissions.removeIf { it == Manifest.permission.WRITE_EXTERNAL_STORAGE }
    }
    Dexter.withActivity(this)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        onSuccess()
                    } else {
                        showToast(this@askForPermission, "Please allow permission to continue")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
}

fun Uri.getImageDimension(): BitmapFactory.Options {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)
    return options
}

fun convertInToBase64(filePath: String?): String {
    val bm = BitmapFactory.decodeFile(filePath)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun Context.getOutputFile(
        extension: String = ".mp4",
        isTempFile: Boolean = false,
        fileName: String? = System.currentTimeMillis().toString()
): File {
    val mediaPath = externalMediaDirs.find { it.absolutePath.contains(packageName) }
    var outputPath = File(mediaPath.toString() + File.separator + "TheWellenessCorner/")
    if (!outputPath.exists())
        outputPath.mkdir()
    if (isTempFile) {
        val tempFileDir = File(outputPath, ".temp/")
        if (!tempFileDir.exists())
            tempFileDir.mkdir()
        outputPath = tempFileDir
    }

    if (fileName.isNullOrEmpty() && extension.isEmpty()) {
        return outputPath
    }
    return File(outputPath, fileName + extension)
}