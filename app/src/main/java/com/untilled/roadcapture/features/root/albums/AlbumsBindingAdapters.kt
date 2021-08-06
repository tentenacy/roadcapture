package com.untilled.roadcapture.features.root.albums

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.extension.getPxFromDp


object AlbumsBindingAdapters {
    @JvmStatic
    @BindingAdapter("albumThumbnailImage")
    fun setAlbumThumbnailImage(view: ShapeableImageView, url: String) {
        view.context.apply {
            val radius = resources.getDimension(R.dimen.album_corner_raduis)
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

    @JvmStatic
    @BindingAdapter("albumProfileImage")
    fun setAlbumProfileImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .circleCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("albumOverlayUsername", "albumOverlayCreatedDate")
    fun setAlbumOverlayText(view: TextView, username: String, createdDate: String) {
        // TODO: createdDate를 지정된 형식으로 변환
        view.text = "$username · $createdDate"
    }

    @JvmStatic
    @BindingAdapter("adjustConstraintEmptyDesc")
    fun adjustConstraint(view: ConstraintLayout, description: String) {
        if (description.isBlank()) {
            val constraints = ConstraintSet()
            constraints.clone(view)
            constraints.connect(
                R.id.textview_item_home_album_title,
                ConstraintSet.BOTTOM,
                view.id,
                ConstraintSet.BOTTOM,
                view.context.getPxFromDp(48f)
            )
            constraints.applyTo(view)
        }
    }
}