package com.untilled.roadcapture.util.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.R
import com.untilled.roadcapture.util.extension.setRippleEffect

object LayoutBindingAdapter {
    @JvmStatic
    @BindingAdapter("clickableLayout")
    fun setClickableLayout(view: View, any: Any?) {
        val context = view.context
        view.setBackgroundColor(context.getColor(R.color.secondaryColor))
        view.setRippleEffect()
    }
}