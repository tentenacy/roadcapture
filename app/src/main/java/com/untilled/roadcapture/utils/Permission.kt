package com.untilled.roadcapture.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.checkSelfPermission(permission: List<String>, logic: () -> Unit) {
    var isGranted = true;
    for (i in permission.indices) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            isGranted = false
            break;
        }
    }
    if (isGranted) {
        logic()
    }
}