package com.untilled.roadcapture.features.root.mystudio

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.untilled.roadcapture.R

object StudiosBindingAdapters {

    @JvmStatic
    @BindingAdapter("studioThumbnailImage", "studioThumbnailImageIndex")
    fun setStudioThumbnailImage(view: ShapeableImageView, url: String, index: String) {
        view.context.apply {

            val radius = resources.getDimension(R.dimen.album_corner_radius)
            val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()
            view.shapeAppearanceModel = shapeAppearanceModel


            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

}