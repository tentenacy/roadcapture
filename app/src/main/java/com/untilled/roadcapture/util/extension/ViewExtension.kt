package com.untilled.roadcapture.util.extension

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat

fun View.setRippleEffect() {
    isClickable = true
    isFocusable = true
    foreground = getDrawableFrom(android.R.attr.selectableItemBackground)
}

fun View.getDrawableFrom(attrId: Int): Drawable? = context.run {
    val typedValue = TypedValue().also { theme.resolveAttribute(attrId, it, true) }
    ContextCompat.getDrawable(this, typedValue.resourceId)
}