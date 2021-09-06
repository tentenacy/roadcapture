package com.untilled.roadcapture.features

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.extension.setRippleEffect


object CommonBindingAdapters {

    @JvmStatic
    @BindingAdapter("clickableTextView")
    fun setClickableTextView(view: TextView, any: Any?) {
        val context = view.context
        view.setTextAppearance(R.style.Button)
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

    @JvmStatic
    @BindingAdapter("rippleEffect")
    fun setRippleEffect(view: View, any: Any?) {
        view.setRippleEffect()
    }

    @JvmStatic
    @BindingAdapter("countFormat")
    fun setCountFormat(view: TextView, count: Int) {

    }

    @JvmStatic
    @BindingAdapter("Image")
    fun setImageView(imageView: ImageView, uri: String?) {
        imageView.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(uri?:R.drawable.plus_dotted_square)
                .centerCrop()
                .into(imageView)
        }
    }
}