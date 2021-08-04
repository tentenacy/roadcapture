package com.untilled.roadcapture.util.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.R
import com.untilled.roadcapture.util.extension.setRippleEffect


object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("clickableTextView")
    fun setClickableTextView(view: TextView, any: Any?) {
        val context = view.context
        view.setTextColor(context.getColor(R.color.white))
        view.setBackgroundColor(context.getColor(R.color.secondaryColor))
        view.setTextAppearance(R.style.TextAppearance_MdcTypographyStyles_Button)
        view.setRippleEffect()
    }
}