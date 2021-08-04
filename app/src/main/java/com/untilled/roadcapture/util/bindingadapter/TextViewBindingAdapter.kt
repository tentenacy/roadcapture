package com.untilled.roadcapture.util.bindingadapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.R
import com.untilled.roadcapture.util.extension.setRippleEffect


object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("clickableTextView")
    fun setClickableTextView(view: TextView, any: Any?) {
        val context = view.context
        view.setTextAppearance(R.style.TextAppearance_MdcTypographyStyles_Button)
        view.setTextColor(ContextCompat.getColor(context, R.color.white))
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.secondaryColor))
        view.setRippleEffect()
    }
}