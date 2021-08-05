package com.untilled.roadcapture.utils.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat

fun View.setRippleEffect() {
    isClickable = true
    isFocusable = true
    foreground = context.getDrawableFrom(android.R.attr.selectableItemBackground)
}

fun Context.getDrawableFrom(attrId: Int): Drawable? = TypedValue().run {
    theme.resolveAttribute(attrId, this, true)
    ContextCompat.getDrawable(this@getDrawableFrom, this.resourceId)
}

fun Context.getPxFromDp(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun Context.getDpFromPx(px: Int) = resources.displayMetrics.density.let { density ->
    when(density) {
        1.0f -> px / (density * 4.0f)
        1.5f -> px / (8 / 3.0f)
        2.0f -> px / (density * 2.0f)
        else -> px / density
    }
}

fun Context.getColor(colorId: Int): Drawable? = ContextCompat.getDrawable(this, colorId)