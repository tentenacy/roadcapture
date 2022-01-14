package com.untilled.roadcapture.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat

fun Context.getDrawableFrom(attrId: Int): Drawable? = TypedValue().run {
    theme.resolveAttribute(attrId, this, true)
    ContextCompat.getDrawable(this@getDrawableFrom, this.resourceId)
}

fun Context.getDrawable(colorId: Int): Drawable? = ContextCompat.getDrawable(this, colorId)