package com.untilled.roadcapture.features

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.extension.setRippleEffect


object CommonBindingAdapter {

    @JvmStatic
    @BindingAdapter("clickableTextView")
    fun setClickableTextView(view: TextView, any: Any?) {
        val context = view.context
        view.setTextAppearance(R.style.TextAppearance_MdcTypographyStyles_Button)
        view.setTextColor(context.getColor(R.color.white))
        view.setBackgroundColor(context.getColor(R.color.secondaryColor))
        view.setRippleEffect()
    }

    @JvmStatic
    @BindingAdapter("clickableLayout")
    fun setClickableLayout(view: View, any: Any?) {
        val context = view.context
        view.setBackgroundColor(context.getColor(R.color.secondaryColor))
        view.setRippleEffect()
    }
}