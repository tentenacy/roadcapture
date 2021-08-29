package com.untilled.roadcapture.features.root.comment

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

object CommentBindingAdapters {
    @JvmStatic
    @BindingAdapter("commentProfileImage")
    fun setCommentProfileImage(view: CircleImageView, url: String) {
        view.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }
}
