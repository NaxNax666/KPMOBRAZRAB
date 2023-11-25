package com.example.kpmobrazrab.utils.context

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

fun Context.isReadExternalStorageGranted() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.READ_EXTERNAL_STORAGE
) == PackageManager.PERMISSION_GRANTED

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.isReadMediaImagesGranted() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.READ_MEDIA_IMAGES
) == PackageManager.PERMISSION_GRANTED

fun Context.openAppSystemSettings() {
    startActivity(
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
    )
}