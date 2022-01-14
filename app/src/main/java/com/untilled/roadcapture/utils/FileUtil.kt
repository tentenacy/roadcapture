package com.untilled.roadcapture.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat
import java.io.File

fun deleteCache(context: Context) {
    try {
        val dir: File = context.cacheDir
        deleteDir(dir)
    } catch (e: java.lang.Exception) {
    }
}

fun deleteDir(dir: File?): Boolean {
    return if (dir != null && dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
        dir.delete()
    } else if (dir != null && dir.isFile) {
        dir.delete()
    } else {
        false
    }
}