package com.untilled.roadcapture.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat

// Milliseconds used for UI animations in Camera
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

fun Activity.setStatusBarTransparent() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Activity.setStatusBarOrigin() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
}

fun Activity.hideKeyboard(editText: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun ImageView.setTint(colorId: Int) {
    DrawableCompat.setTint(DrawableCompat.wrap(drawable), colorId)
}

fun View.setRippleEffect() {
    isClickable = true
    isFocusable = true
    foreground = context.getDrawableFrom(android.R.attr.selectableItemBackground)
}