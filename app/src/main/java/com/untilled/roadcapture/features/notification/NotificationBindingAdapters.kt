package com.untilled.roadcapture.features.notification

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

object NotificationBindingAdapters {
    @JvmStatic
    @BindingAdapter("notificationProfileImage")
    fun setNotificationProfileImage(view: CircleImageView, url: String) {
        view.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("notificationThumbnailImage")
    fun setNotificationThumbnailImage(view: ImageView, url: String) {
        view.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("notificationUsername","notificationContent")
    fun setNotificationContent(view: TextView, username: String, content: String){
        view.text = Html.fromHtml("<b>" + username + "</b>" + content)
    }
}