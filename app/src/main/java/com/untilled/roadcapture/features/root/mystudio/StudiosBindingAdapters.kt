package com.untilled.roadcapture.features.root.mystudio

import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.extension.getPxFromDp

object StudiosBindingAdapters {

    @JvmStatic
    @BindingAdapter("studioThumbnailImage", "studioThumbnailImageIndex")
    fun setStudioThumbnailImage(view: ShapeableImageView, url: String, index: String) {
        view.context.apply {

            if(index.toInt() % 2 == 0) {
                val radius = resources.getDimension(R.dimen.album_corner_raduis)
                val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                    .setTopRightCorner(CornerFamily.ROUNDED, radius)
                    .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                    .build()
                view.shapeAppearanceModel = shapeAppearanceModel
            }

            else {
                val radius = resources.getDimension(R.dimen.album_corner_raduis)
                val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .build()
                view.shapeAppearanceModel = shapeAppearanceModel
            }

            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("reverseItem")
    fun setReverseItem(view: ConstraintLayout, index: String) {
        //TODO 여백 설정, 구분선
        if(index.toInt() % 2 == 0) {
            val constraints = ConstraintSet()
            constraints.clone(view)
            constraints.connect(
                R.id.textview_item_studios_album_date,
                ConstraintSet.START,
                view.id,
                ConstraintSet.START,
                0
            )
            constraints.connect(
                R.id.textview_item_studios_album_title,
                ConstraintSet.START,
                R.id.imageview_item_studios_album_thumbnail,
                ConstraintSet.END,
                0
            )
            constraints.connect(
                R.id.divider_item_studios_album_circle,
                ConstraintSet.START,
                R.id.textview_item_studios_album_date,
                ConstraintSet.END
            )
            constraints.connect(
                R.id.divider_item_studios_album_line,
                ConstraintSet.START,
                R.id.divider_item_studios_album_circle,
                ConstraintSet.END
            )
            constraints.connect(
                R.id.divider_item_studios_album_line,
                ConstraintSet.END,
                view.id,
                ConstraintSet.END
            )
            constraints.setMargin(R.id.textview_item_studios_album_title,ConstraintSet.START,150)
            constraints.setMargin(R.id.divider_item_studios_album_circle,ConstraintSet.START,20)
            constraints.applyTo(view)
        }
        else{
            val constraints = ConstraintSet()
            constraints.clone(view)
            constraints.connect(
                R.id.textview_item_studios_album_date,
                ConstraintSet.END,
                view.id,
                ConstraintSet.END,
                0
            )
            constraints.connect(
                R.id.textview_item_studios_album_title,
                ConstraintSet.END,
                R.id.imageview_item_studios_album_thumbnail,
                ConstraintSet.START,
                0
            )
            constraints.connect(
                R.id.divider_item_studios_album_circle,
                ConstraintSet.END,
                R.id.textview_item_studios_album_date,
                ConstraintSet.START
            )
            constraints.connect(
                R.id.divider_item_studios_album_line,
                ConstraintSet.END,
                R.id.divider_item_studios_album_circle,
                ConstraintSet.START
            )
            constraints.connect(
                R.id.divider_item_studios_album_line,
                ConstraintSet.START,
                view.id,
                ConstraintSet.START
            )
            constraints.setMargin(R.id.textview_item_studios_album_title,ConstraintSet.END,150)
            constraints.setMargin(R.id.divider_item_studios_album_circle,ConstraintSet.END,20)
            constraints.applyTo(view)
        }
    }
}