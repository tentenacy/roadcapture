package com.untilled.roadcapture.utils.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener

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

fun Fragment.checkPermission(permission: String) =
    ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun Fragment.requestSinglePermission(permission: String, basicPermissionListener: PermissionListener, snackBarPermissionListener: PermissionListener) {
    if(!checkPermission(permission)) {
        Dexter.withContext(requireContext())
            .withPermission(permission)
            .withListener(
                CompositePermissionListener(
                    basicPermissionListener,
                    snackBarPermissionListener
                )
            ).check()
    }
}

fun basicPermissionListener(granted: () -> Unit): PermissionListener =
    object : PermissionListener {
        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
            granted()
        }

        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}
        override fun onPermissionRationaleShouldBeShown(
            p0: PermissionRequest?,
            p1: PermissionToken?
        ) {
        }
    }

fun snackBarPermissionListener(view: View?, deniedMessage: String): PermissionListener =
    SnackbarOnDeniedPermissionListener.Builder
        .with(view, deniedMessage)
        .withOpenSettingsButton("설정")
        .withCallback(object : Snackbar.Callback() {
            override fun onShown(snackbar: Snackbar) {}
            override fun onDismissed(snackbar: Snackbar, event: Int) {}
        }).build()

const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
const val CAMERA = Manifest.permission.CAMERA