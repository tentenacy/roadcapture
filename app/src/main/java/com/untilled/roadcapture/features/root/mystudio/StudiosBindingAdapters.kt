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
    @BindingAdapter("studioThumbnailImage")
    fun setStudioThumbnailImage(view: ShapeableImageView, url: String) {
        view.context.apply {

            val radius = resources.getDimension(R.dimen.studio_album_corner_radius)
            val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
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