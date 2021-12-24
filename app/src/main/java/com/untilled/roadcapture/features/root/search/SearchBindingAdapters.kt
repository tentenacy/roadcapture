package com.untilled.roadcapture.features.root.search

import android.text.InputFilter
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.extension.getPxFromDp

object SearchBindingAdapters {

    @JvmStatic
    @BindingAdapter("searchEditText")
    fun setSearchEditText(view: EditText, any: Any?) {
        val context = view.context
        context.apply {
            view.minHeight = getPxFromDp(0.0f)
            view.setPadding(0, getPxFromDp(12.0f), 0, getPxFromDp(12.0f))
            view.maxLines = 1
            view.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
            view.setBackgroundColor(context.getColor(android.R.color.transparent))
        }
    }

    @JvmStatic
    @BindingAdapter("titleSearchThumbnailImage")
    fun setTitleSearchThumbnailImage(view: ShapeableImageView, url: String) {
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

    @JvmStatic
    @BindingAdapter("titleSearchProfileImage")
    fun setTitleSearchProfileImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .circleCrop()
            .into(view)
    }
}