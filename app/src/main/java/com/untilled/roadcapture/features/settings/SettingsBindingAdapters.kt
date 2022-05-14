package com.untilled.roadcapture.features.settings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.R

object SettingsBindingAdapters {

    @JvmStatic
    @BindingAdapter("userProviderImage")
    fun setUserProviderImage(view: ImageView, provider: String?) {
        when(provider) {
            "naver" -> R.drawable.ic_naver
            "kakao" -> R.drawable.ic_kakao
            "google" -> R.drawable.ic_google
            "facebook" -> R.drawable.ic_facebook
            else -> null
        }?.let { view.setImageResource(it) }
    }
}